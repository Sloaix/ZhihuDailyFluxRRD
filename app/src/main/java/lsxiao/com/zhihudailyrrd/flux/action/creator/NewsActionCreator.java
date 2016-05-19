package lsxiao.com.zhihudailyrrd.flux.action.creator;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.flux.action.NewsAction;
import lsxiao.com.zhihudailyrrd.flux.action.creator.base.BaseActionCreator;
import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.service.base.DataLayer;
import lsxiao.com.zhihudailyrrd.util.BundleUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 新闻的ActionCreator
 * author lsxiao
 * date 2016-05-09 17:25
 */
public class NewsActionCreator extends BaseActionCreator {
    public NewsActionCreator(Dispatcher dispatcher, DataLayer dataLayer) {
        super(dispatcher, dataLayer);
    }

    public void fetchListNews() {
        //分发开始刷新列表事件
        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_LIST_NEWS_FETCH_START));

        //缓存数据源
        Observable<TodayNews> cache = getDataLayer().getDailyService().getLatestTodayNews();

        //服务端数据源
        Observable<TodayNews> network = getDataLayer().getDailyService().getTodayNews();

        //输出前缓存一下
        network = network.doOnNext(new Action1<TodayNews>() {
            @Override
            public void call(TodayNews todayNews) {
                getDataLayer().getDailyService().cacheTodayNews(todayNews);
            }
        });

        //没有网络数据再使用缓存数据
        Observable<TodayNews> source = Observable
                .concat(network, cache)
                //依次遍历序列中的数据源,返回第一个符合条件的数据源
                .first(new Func1<TodayNews, Boolean>() {
                    @Override
                    public Boolean call(TodayNews todayNews) {
                        return todayNews != null;
                    }
                });

        source.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TodayNews>() {
                    @Override
                    public void call(TodayNews todayNews) {
                        //封装数据
                        Bundle bundle = BundleUtil.newInstance();
                        bundle.putSerializable(BundleKey.TODAY_NEWS, todayNews);
                        //分发Action
                        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_LIST_NEWS_FETCH_FINISH, bundle));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //封装异常
                        Bundle bundle = BundleUtil.withThrowable(throwable);
                        //分发Action
                        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_LIST_NEWS_FETCH_ERROR, bundle));
                    }
                });

    }

    public void fetchDetailNews(TodayNews.Story mStory) {
        //分发开始刷新列表事件
        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_DETAIL_NEWS_FETCH_START));

        //服务端数据源
        Observable<News> network = getDataLayer().getDailyService().getNews(mStory.getId());

        //缓存数据源
        Observable<News> cache = getDataLayer().getDailyService().getLocalNews(String.valueOf(mStory.getId()));


        //输出数据前缓存到本地
        network = network.doOnNext(new Action1<News>() {
            @Override
            public void call(News news) {
                getDataLayer().getDailyService().cacheNews(news);
            }
        });

        //返回第一个不为空的Observable对象  
        Observable<News> newsObservable = Observable
                .concat(cache, network)
                .first(new Func1<News, Boolean>() {
                    @Override
                    public Boolean call(News news) {
                        //如果本地数据存在的话
                        return news != null;
                    }
                });

        //订阅事件序列
        newsObservable.subscribeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)//延迟一秒显示，让loading过渡更自然
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理订阅的事件序列
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        //封装数据
                        Bundle bundle = BundleUtil.newInstance();
                        bundle.putSerializable(BundleKey.NEWS, news);
                        //分发Action给Store
                        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_DETAIL_NEWS_FETCH_FINISH, bundle));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //封装异常
                        Bundle bundle = BundleUtil.withThrowable(throwable);
                        //分发Action给Store
                        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_DETAIL_NEWS_FETCH_ERROR, bundle));
                    }
                });
    }
}

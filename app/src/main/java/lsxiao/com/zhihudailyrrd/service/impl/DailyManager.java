package lsxiao.com.zhihudailyrrd.service.impl;


import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.StartImage;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.service.DataLayer;
import lsxiao.com.zhihudailyrrd.util.SpUtil;
import rx.Observable;
import rx.Subscriber;

/**
 * @author lsxiao
 * date 2015-11-03 22:28
 */
public class DailyManager extends BaseManager implements DataLayer.DailyService {
    @Override
    public Observable<TodayNews> getTodayNews() {
        return getApi().getTodayNews();
    }

    @Override
    public Observable<StartImage> getStartImage() {
        return getApi().getStartImage();
    }

    @Override
    public Observable<News> getNews(long newsId) {
        return getApi().getNews(newsId);
    }

    @Override
    public void cacheTodayNews(final TodayNews todayNews) {
        SpUtil.saveOrUpdate(todayNews.getDate(), getGson().toJson(todayNews));
    }

    @Override
    public void cacheNews(final News news) {
        SpUtil.saveOrUpdate(String.valueOf(news.getId()), getGson().toJson(news));
    }

    @Override
    public Observable<TodayNews> getLocalTodayNews(final String date) {
        return Observable.create(new Observable.OnSubscribe<TodayNews>() {
            @Override
            public void call(Subscriber<? super TodayNews> subscriber) {
                try {
                    subscriber.onStart();
                    String json = SpUtil.find(date);
                    TodayNews todayNews = getGson().fromJson(json, TodayNews.class);
                    subscriber.onNext(todayNews);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<News> getLocalNews(final String id) {
        return Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                try {
                    subscriber.onStart();
                    String json = SpUtil.find(id);
                    News news = getGson().fromJson(json, News.class);
                    subscriber.onNext(news);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}

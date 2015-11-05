package lsxiao.com.zhihudailyrrd.view.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.Bind;
import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.utils.HtmlUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author lsxiao
 * @date 2015-11-05 10:27
 */
public class NewsFragment extends BaseFragment {
    @Bind(R.id.wv_news)
    WebView mWvNews;
    @Bind(R.id.cpb_loading)
    ContentLoadingProgressBar mCpbLoading;
    private long mNewsId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    public static Fragment newInstance(long newsId) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.NEWS_ID, newsId);
        Fragment Fragment = new NewsFragment();
        Fragment.setArguments(bundle);
        return Fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mNewsId = getArguments().getLong(BundleKey.NEWS_ID);
        init();
        remoteData();
    }

    private void init() {
        setHasOptionsMenu(true);
        mWvNews.getSettings().setLoadsImagesAutomatically(true);
        //设置 缓存模式
        mWvNews.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        mWvNews.getSettings().setDomStorageEnabled(true);
    }

    private void showLoading() {
        mCpbLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mCpbLoading.setVisibility(View.GONE);
    }

    private void remoteData() {
        getDataLayer()
                .getDailyService()
                .getNews(mNewsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoading();
                    }
                })
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        String css = HtmlUtil.createCssTag(news.getCssList());
                        String js = HtmlUtil.createJsTag(news.getJsList());
                        String htmlData =css + news.getBody() + js;
                        mWvNews.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                        hideLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        hideLoading();
                    }
                });
    }
}

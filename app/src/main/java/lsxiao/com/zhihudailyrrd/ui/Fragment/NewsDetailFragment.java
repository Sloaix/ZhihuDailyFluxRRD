package lsxiao.com.zhihudailyrrd.ui.Fragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;
import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.util.HtmlUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author lsxiao
 * date 2015-11-05 10:27
 */
public class NewsDetailFragment extends BaseFragment {
    @Bind(R.id.wv_news)
    WebView mWvNews;
    @Bind(R.id.cpb_loading)
    ContentLoadingProgressBar mCpbLoading;
    @Bind(R.id.iv_header)
    ImageView mImageView;
    @Bind(R.id.tv_source)
    TextView mTvSource;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nested_view)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;
    private TodayNews.Story mStory;
    private News mNews;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    public static Fragment newInstance(TodayNews.Story story) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.STORY, story);
        Fragment Fragment = new NewsDetailFragment();
        Fragment.setArguments(bundle);
        return Fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mStory = (TodayNews.Story) getArguments().getSerializable(BundleKey.STORY);
        init();
        loadData();
    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true);
        mNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mNestedScrollView.setElevation(0);
        mWvNews.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWvNews.setElevation(0);
        mWvNews.getSettings().setLoadsImagesAutomatically(true);
        //设置 缓存模式
        mWvNews.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        mWvNews.getSettings().setDomStorageEnabled(true);
        //为可折叠toolbar设置标题
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
    }

    /**
     * 显示Loading
     */
    private void showLoading() {
        mCpbLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏Loading
     */
    private void hideLoading() {
        mCpbLoading.setVisibility(View.GONE);
    }

    private void loadData() {
        Observable<News> network = getDataLayer().getDailyService().getNews(mStory.getId());
        Observable<News> cache = getDataLayer().getDailyService().getLocalNews(String.valueOf(mStory.getId()));


        //输出数据前缓存
        network.doOnNext(new Action1<News>() {
            @Override
            public void call(News news) {
                getDataLayer().getDailyService().cacheNews(news);
            }
        });

        //默认先从本地取数据
        Observable<News> source = Observable.concat(cache, network).first(new Func1<News, Boolean>() {
            @Override
            public Boolean call(News news) {
                //如果本地数据存在的话
                return news != null;
            }
        });

        source.compose(this.<News>bindUntilEvent(FragmentEvent.PAUSE))
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        if (news != null) {
                            getDataLayer().getDailyService().cacheNews(news);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //解除订阅关系后停隐藏刷新progress
                        hideLoading();
                        if (null == mNews) {
                            mTvLoadEmpty.setVisibility(View.GONE);
                            mTvLoadError.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        //隐藏progress
                        hideLoading();
                        mNews = news;
                        if (mNews == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            Picasso.with(getActivity())
                                    .load(news.getImage())
                                    .into(mImageView);
                            mTvSource.setText(news.getImageSource());

                            String htmlData = HtmlUtil.createHtmlData(news);
                            mWvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        //隐藏progress
                        hideLoading();
                    }
                });
    }

}

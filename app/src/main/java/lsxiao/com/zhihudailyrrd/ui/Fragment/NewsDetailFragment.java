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

import butterknife.Bind;
import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.flux.store.NewsDetailStore;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.util.HtmlUtil;
import lsxiao.com.zhihudailyrrd.util.RxBus;
import rx.functions.Action1;

/**
 * @author lsxiao
 *         date 2015-11-05 10:27
 */
public class NewsDetailFragment extends BaseFragment implements View.OnClickListener {
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
    private NewsDetailStore mNewsDetailStore;

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
        if (null != savedInstanceState) {
            mNewsDetailStore = (NewsDetailStore) savedInstanceState.getSerializable(BundleKey.NEWS_DETAIL_STORE);
        } else if (mNewsDetailStore == null) {
            mNewsDetailStore = new NewsDetailStore();
        }
        init();
        onStoreChange();
        dispatchFetchDetailNews();
    }

    /**
     * 初始化
     */
    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTvLoadError.setOnClickListener(this);

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
     * 监听Store状态的改变
     */
    private void onStoreChange() {
        getSubscription().add(RxBus.instance()
                .toObservable(NewsDetailStore.FetchChangeEvent.class
                )
                .subscribe(new Action1<NewsDetailStore.FetchChangeEvent>() {
                    @Override
                    public void call(NewsDetailStore.FetchChangeEvent fetchChangeEvent) {
                        render();
                    }
                }));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BundleKey.NEWS_DETAIL_STORE, mNewsDetailStore);
    }

    /**
     * 渲染UI
     */
    @SuppressWarnings("ResourceType")
    private void render() {
        mTvLoadEmpty.setVisibility(mNewsDetailStore.getEmptyViewVis());
        mTvLoadError.setVisibility(mNewsDetailStore.getErrorViewVis());
        mCpbLoading.setVisibility(mNewsDetailStore.isShowLoadView() ? View.VISIBLE : View.GONE);
        if (!mNewsDetailStore.isEmpty() && mNewsDetailStore.isFinish()) {
            News news = mNewsDetailStore.getData();
            Picasso.with(getActivity()).load(news.getImage()).into(mImageView);
            mTvSource.setText(news.getImageSource());
            String htmlData = HtmlUtil.createHtmlData(news);
            mWvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
        }
    }


    /**
     * 分发拉取详细新闻action
     */
    private void dispatchFetchDetailNews() {
        getActionCreatorManager().getNewsActionCreator().fetchDetailNews(mStory);
    }

    /**
     * Store的订阅和注销由基类控制,这里只需要返回一个Store实例
     * 这个方法在afterCreate()之前就会被调用
     *
     * @return BaseStore
     */
    @Override
    protected BaseStore getStore() {
        if (mNewsDetailStore == null) {
            mNewsDetailStore = new NewsDetailStore();
        }
        return mNewsDetailStore;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.tv_load_error: {
                dispatchFetchDetailNews();
                break;
            }
            default:
        }
    }
}

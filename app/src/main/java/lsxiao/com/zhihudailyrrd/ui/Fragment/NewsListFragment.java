package lsxiao.com.zhihudailyrrd.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.util.ArrayList;

import butterknife.Bind;
import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.base.DividerItemDecoration;
import lsxiao.com.zhihudailyrrd.flux.action.NewsAction;
import lsxiao.com.zhihudailyrrd.flux.store.NewsListStore;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.ui.Activity.NewsDetailActivity;
import lsxiao.com.zhihudailyrrd.ui.Adapter.NewsListAdapter;
import lsxiao.com.zhihudailyrrd.util.RxBus;
import rx.functions.Action1;

/**
 * @author lsxiao
 *         date 2015-11-03 23:43
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, BaseSliderView.OnSliderClickListener {
    public static final int SPAN_COUNT = 1;//列数

    NewsListAdapter mNewsListAdapter;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rcv_news_list)
    RecyclerView mRcvNewsList;
    @Bind(R.id.srl_news_list)
    SwipeRefreshLayout mSrlNewsList;
    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;

    NewsListStore mNewsListStore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            mNewsListStore = (NewsListStore) savedInstanceState.getSerializable(BundleKey.NEWS_LIST_STORE);
        } else if (mNewsListStore == null) {
            mNewsListStore = new NewsListStore();
        }
        init();
        onStoreChange();
        dispatchFetchListNews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BundleKey.NEWS_LIST_STORE, mNewsListStore);
    }

    @Override
    protected BaseStore getStore() {
        if (mNewsListStore == null) {
            mNewsListStore = new NewsListStore();
        }
        return mNewsListStore;
    }

    public static Fragment newInstance() {
        return new NewsListFragment();
    }


    private void init() {
        mToolbar.setTitle(getString(R.string.today_news));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

        mTvLoadError.setOnClickListener(this);

        //为下拉刷新组件绑定监听器
        mSrlNewsList.setOnRefreshListener(this);

        //为下拉刷新组件设置CircleProgress主色调
        mSrlNewsList.setColorSchemeColors(getResources().getColor(R.color.color_primary));

        //实例化布局管理器
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRcvNewsList.setLayoutManager(manager);

        //初始化并绑定适配器
        mNewsListAdapter = new NewsListAdapter(getActivity(), new ArrayList<TodayNews.Story>(), new ArrayList<TodayNews.Story>(),
                this, this);
        mRcvNewsList.setAdapter(mNewsListAdapter);

        //设置ItemView的divider
        mRcvNewsList.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
    }


    /**
     * 监听Store状态改变
     */
    private void onStoreChange() {
        //拉取的数据改变事件
        getSubscription().add(RxBus.instance().toObservable(NewsListStore.FetchChangeEvent.class)
                .subscribe(new Action1<NewsListStore.FetchChangeEvent>() {
                    @Override
                    public void call(NewsListStore.FetchChangeEvent fetchChangeEvent) {
                        render();
                    }
                }));

        //ItemClick改变事件
        getSubscription().add(RxBus.instance().toObservable(NewsListStore.ItemClickChangeEvent.class)
                .subscribe(new Action1<NewsListStore.ItemClickChangeEvent>() {
                    @Override
                    public void call(NewsListStore.ItemClickChangeEvent itemClickChangeEvent) {
                        if (null == itemClickChangeEvent) {
                            return;
                        }
                        TodayNews.Story story = mNewsListAdapter.getItemData(itemClickChangeEvent.position);
                        NewsDetailActivity.start(getActivity(), story);
                    }
                }));

        //SliderClick改变事件
        getSubscription().add(RxBus.instance().toObservable(NewsListStore.SliderClickChangeEvent.class)
                .subscribe(new Action1<NewsListStore.SliderClickChangeEvent>() {
                    @Override
                    public void call(NewsListStore.SliderClickChangeEvent sliderClickChangeEvent) {
                        if (null == sliderClickChangeEvent) {
                            return;
                        }
                        NewsDetailActivity.start(getActivity(), sliderClickChangeEvent.story);
                    }
                }));

    }

    /**
     * 渲染UI
     */
    @SuppressWarnings("ResourceType")
    private void render() {
        mSrlNewsList.setRefreshing(mNewsListStore.isShowLoadView());
        mTvLoadEmpty.setVisibility(mNewsListStore.getEmptyViewVis());
        mTvLoadError.setVisibility(mNewsListStore.getErrorViewVis());
        if (!mNewsListStore.isEmpty() && mNewsListStore.isFinish()) {
            TodayNews todayNews = mNewsListStore.getTodayNews();
            mNewsListAdapter.setStories(todayNews.getStories(), todayNews.getTopStories());
            mNewsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        dispatchFetchListNews();
    }

    /**
     * 分发拉取列表新闻action
     */
    private void dispatchFetchListNews() {
        getActionCreatorManager().getNewsActionCreator().fetchListNews();
    }

    /**
     * 分发item click action
     *
     * @param position item position.
     */
    private void dispatchItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.POSITION, position);
        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_NEWS_ITEM_LIST_CLICK, bundle));
    }

    /**
     * 分发slider click action
     *
     * @param story TodayNews.Story
     */
    private void dispatchSliderClick(TodayNews.Story story) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.STORY, story);
        getDispatcher().dispatch(new NewsAction(NewsAction.ACTION_NEWS_SLIDER_LIST_CLICK, bundle));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.tv_load_error: {
                dispatchFetchListNews();
                break;
            }
            default: {
                final int position = mRcvNewsList.getChildAdapterPosition(v);
                if (RecyclerView.NO_POSITION != position) {
                    dispatchItemClick(position);
                }
            }
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        TodayNews.Story story = (TodayNews.Story) slider.getBundle().getSerializable(BundleKey.STORY);
        if (story != null) {
            dispatchSliderClick(story);
        }
    }
}

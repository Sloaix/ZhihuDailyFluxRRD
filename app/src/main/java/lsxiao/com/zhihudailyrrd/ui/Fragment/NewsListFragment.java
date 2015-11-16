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
import android.widget.Toast;

import com.trello.rxlifecycle.FragmentEvent;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.Bind;
import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.base.DividerItemDecoration;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.ui.Activity.NewsDetailActivity;
import lsxiao.com.zhihudailyrrd.ui.Adapter.NewsListAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author lsxiao
 *         date 2015-11-03 23:43
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        init();
        loadData();
    }


    public static Fragment newInstance() {
        return new NewsListFragment();
    }

    private void init() {
        mToolbar.setTitle(getString(R.string.today_news));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        //为下拉刷新组件绑定监听器
        mSrlNewsList.setOnRefreshListener(this);

        //为下拉刷新组件设置CircleProgress主色调
        mSrlNewsList.setColorSchemeColors(getResources().getColor(R.color.color_primary));

        //实例化布局管理器
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRcvNewsList.setLayoutManager(manager);

        //初始化并绑定适配器
        mNewsListAdapter = new NewsListAdapter(getActivity(), new ArrayList<TodayNews.Story>(), new ArrayList<TodayNews.Story>(),
                this);
        mRcvNewsList.setAdapter(mNewsListAdapter);

        //设置ItemView的divider
        mRcvNewsList.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
    }

    private void loadData() {
        String nowDateStr = DateTime.now().minusDays(1).toString("yyyyMMdd");
        Observable<TodayNews> cache = getDataLayer().getDailyService().getLocalTodayNews(nowDateStr);
        Observable<TodayNews> network = getDataLayer().getDailyService().getTodayNews();

        //输出前缓存一下
        network.doOnNext(new Action1<TodayNews>() {
            @Override
            public void call(TodayNews todayNews) {
                getDataLayer().getDailyService().cacheTodayNews(todayNews);
            }
        });


        //先获取缓存里面的数据
        Observable<TodayNews> source = Observable
                .concat(cache, network)
                .first(new Func1<TodayNews, Boolean>() {
                    @Override
                    public Boolean call(TodayNews todayNews) {
                        //如果本地数据存在的话
                        return todayNews != null;
                    }
                });

        source.compose(this.<TodayNews>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //解除订阅关系后停止刷新
                        hideProgress();
                        if (mNewsListAdapter.getStories().isEmpty()) {
                            mTvLoadEmpty.setVisibility(View.GONE);
                            mTvLoadError.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TodayNews>() {
                    @Override
                    public void call(TodayNews todayNews) {
                        hideProgress();
                        if (todayNews.getStories() == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mNewsListAdapter.setStories(todayNews.getStories(), todayNews.getTopStories());
                            mNewsListAdapter.notifyDataSetChanged();
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        //停止显示刷新动画
                        hideProgress();
                        Toast.makeText(getActivity(), getString(R.string.load_fail), Toast.LENGTH_SHORT).show();
                        mTvLoadEmpty.setVisibility(View.GONE);
                        mTvLoadError.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onClick(View v) {
        final int position = mRcvNewsList.getChildAdapterPosition(v);
        if (RecyclerView.NO_POSITION != position) {
            TodayNews.Story story = mNewsListAdapter.getItemData(position);
            NewsDetailActivity.start(getActivity(), story);
        }
    }

    public void showProgress() {
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                //刷新动画
                mSrlNewsList.setRefreshing(true);
            }
        });
    }

    public void hideProgress() {
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                mSrlNewsList.setRefreshing(false);
            }
        });
    }
}

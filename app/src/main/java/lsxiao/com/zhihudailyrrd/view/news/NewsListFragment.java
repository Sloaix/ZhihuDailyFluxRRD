package lsxiao.com.zhihudailyrrd.view.news;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.base.DividerItemDecoration;
import lsxiao.com.zhihudailyrrd.model.LatestNews;
import lsxiao.com.zhihudailyrrd.model.Story;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author lsxiao
 * @date 2015-11-03 23:43
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @Bind(R.id.rv_news_list)
    RecyclerView mRvNewsList;
    @Bind(R.id.srl_news_list)
    SwipeRefreshLayout mSrlNewsList;
    //数据源适配器
    NewsAdapter mNewsAdapter;
    private OnNewsClickListener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        init();
        remoteData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final FragmentActivity activity = getActivity();
        if (activity instanceof OnNewsClickListener) {
            mListener = (OnNewsClickListener) activity;
        } else {
            throw new ClassCastException("the activity must implement OnNewsClickListener");
        }
    }

    public static Fragment newInstance() {
        Fragment fragment = new NewsListFragment();
        return fragment;
    }

    private void init() {
        mSrlNewsList.setOnRefreshListener(this);
        mSrlNewsList.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRvNewsList.setLayoutManager(manager);

        mNewsAdapter = new NewsAdapter(getActivity(), new ArrayList<Story>(), this);
        mRvNewsList.setAdapter(mNewsAdapter);

        mRvNewsList.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
    }

    private void remoteData() {
        getDataLayer().getDailyService()
                .getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                //显示刷新动画
                                startRefresh();
                            }
                        });
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Func1<LatestNews, List<Story>>() {
                    @Override
                    public List<Story> call(LatestNews latestNews) {
                        return latestNews.getStories();
                    }
                })
                .subscribe(new Action1<List<Story>>() {
                    @Override
                    public void call(List<Story> stories) {
                        mNewsAdapter.setStories(stories);
                        mNewsAdapter.notifyDataSetChanged();
                        stopRefresh();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //停止显示刷新动画
                        stopRefresh();
                        final String tip = "刷新失败";
                        Toast.makeText(getActivity(), tip, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onRefresh() {
        remoteData();
    }

    private void startRefresh() {
        mSrlNewsList.setRefreshing(true);
    }

    private void stopRefresh() {
        mSrlNewsList.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        final int position = mRvNewsList.getChildAdapterPosition(v);
        if (RecyclerView.NO_POSITION != position) {
            Story story = mNewsAdapter.getStories().get(position);
            if (mListener != null) {
                mListener.click(story);
            }
        }
    }

    public interface OnNewsClickListener {
        void click(Story story);
    }
}

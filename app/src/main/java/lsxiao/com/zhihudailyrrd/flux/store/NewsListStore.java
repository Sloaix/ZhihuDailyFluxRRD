package lsxiao.com.zhihudailyrrd.flux.store;

import android.os.Bundle;

import java.io.Serializable;

import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.flux.action.NewsAction;
import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.util.BundleUtil;

/**
 * 对应ListNewsFragment
 * author lsxiao
 * date 2016-05-09 21:18
 */
public class NewsListStore extends BaseStore {
    private TodayNews todayNews;
    private Throwable mThrowable;
    private FetchStatus mFetchStatus = FetchStatus.INIT;

    private enum FetchStatus implements Serializable {
        INIT, LOADING, FINISH, ERROR
    }

    @Override
    public void onAction(BaseAction action) {
        switch (action.getType()) {
            case NewsAction.ACTION_LIST_NEWS_FETCH_START: {
                mFetchStatus = FetchStatus.LOADING;
                mChangeEvent = new FetchChangeEvent();
                emitStoreChange();
                break;
            }
            case NewsAction.ACTION_LIST_NEWS_FETCH_FINISH: {
                Bundle bundle = action.getData();
                if (null != bundle && !bundle.isEmpty()) {
                    todayNews = (TodayNews) bundle.getSerializable(BundleKey.TODAY_NEWS);
                }
                mFetchStatus = FetchStatus.FINISH;
                mChangeEvent = new FetchChangeEvent();
                emitStoreChange();
                break;
            }
            case NewsAction.ACTION_LIST_NEWS_FETCH_ERROR: {
                mFetchStatus = FetchStatus.ERROR;
                mChangeEvent = new FetchChangeEvent();
                mThrowable = BundleUtil.getThrowable(action.getData());
                emitStoreChange();
                break;
            }
            case NewsAction.ACTION_NEWS_ITEM_LIST_CLICK: {
                Bundle bundle = action.getData();
                if (null != bundle && !bundle.isEmpty()) {
                    int position = bundle.getInt(BundleKey.POSITION);
                    mChangeEvent = new ItemClickChangeEvent(position);
                    emitStoreChange();
                }
                break;
            }
            case NewsAction.ACTION_NEWS_SLIDER_LIST_CLICK: {
                Bundle bundle = action.getData();
                if (null != bundle && !bundle.isEmpty()) {
                    TodayNews.Story story = (TodayNews.Story) bundle.getSerializable(BundleKey.STORY);
                    mChangeEvent = new SliderClickChangeEvent(story);
                    emitStoreChange();
                }
                break;
            }
            default:
        }
    }

    public boolean isShowLoadView() {
        return isLoading();
    }

    public int getEmptyViewVis() {
        return isEmpty() && !isError() && isFinish() ? VIS : GONE;
    }

    public int getErrorViewVis() {
        return isError() && isEmpty() ? VIS : GONE;
    }


    public TodayNews getTodayNews() {
        return todayNews;
    }

    public boolean isLoading() {
        return mFetchStatus == FetchStatus.LOADING;
    }

    public boolean isFinish() {
        return mFetchStatus == FetchStatus.FINISH;
    }

    public boolean isError() {
        return mFetchStatus == FetchStatus.ERROR;
    }

    public boolean isEmpty() {
        return todayNews == null;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    @Override
    protected ChangeEvent getChangeEvent() {
        return mChangeEvent;
    }


    public class FetchChangeEvent implements ChangeEvent {

    }

    public class ItemClickChangeEvent implements ChangeEvent {
        public int position;

        public ItemClickChangeEvent(int position) {
            this.position = position;
        }
    }

    public class SliderClickChangeEvent implements ChangeEvent {
        public TodayNews.Story story;

        public SliderClickChangeEvent(TodayNews.Story story) {
            this.story = story;
        }
    }
}

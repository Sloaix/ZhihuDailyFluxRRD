package lsxiao.com.zhihudailyrrd.flux.store;

import android.os.Bundle;

import java.io.Serializable;

import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.flux.action.NewsAction;
import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.util.BundleUtil;

/**
 * 对应DetailNewsFragment
 * author lsxiao
 * date 2016-05-09 17:26
 */
public class NewsDetailStore extends BaseStore {
    private News news;
    private Throwable mThrowable;
    private FetchStatus mFetchStatus = FetchStatus.INIT;

    private enum FetchStatus implements Serializable {
        INIT, LOADING, FINISH, ERROR
    }

    @Override
    public void onAction(BaseAction action) {
        switch (action.getType()) {
            case NewsAction.ACTION_DETAIL_NEWS_FETCH_START: {
                mFetchStatus = FetchStatus.LOADING;
                mChangeEvent = new FetchChangeEvent();
                emitStoreChange();
                break;
            }
            case NewsAction.ACTION_DETAIL_NEWS_FETCH_FINISH: {
                Bundle bundle = action.getData();
                if (null != bundle && !bundle.isEmpty()) {
                    news = (News) bundle.getSerializable(BundleKey.NEWS);
                }
                mFetchStatus = FetchStatus.FINISH;
                mChangeEvent = new FetchChangeEvent();
                emitStoreChange();
                break;
            }
            case NewsAction.ACTION_DETAIL_NEWS_FETCH_ERROR: {
                mFetchStatus = FetchStatus.ERROR;
                mChangeEvent = new FetchChangeEvent();
                mThrowable = BundleUtil.getThrowable(action.getData());
                emitStoreChange();
                break;
            }
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


    public News getData() {
        return news;
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
        return news == null;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    @Override
    protected BaseStore.ChangeEvent getChangeEvent() {
        return mChangeEvent;
    }

    public class FetchChangeEvent implements BaseStore.ChangeEvent {

    }
}

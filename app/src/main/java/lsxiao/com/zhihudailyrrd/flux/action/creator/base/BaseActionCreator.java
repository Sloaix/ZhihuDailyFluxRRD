package lsxiao.com.zhihudailyrrd.flux.action.creator.base;

import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.service.base.DataLayer;

/**
 * ActionCreator基类，所有ActionCreator都需要继承此类
 * author lsxiao
 * date 2016-05-09 17:21
 */
public abstract class BaseActionCreator {
    Dispatcher mDispatcher;
    DataLayer mDataLayer;

    public BaseActionCreator(Dispatcher dispatcher, DataLayer dataLayer) {
        mDispatcher = dispatcher;
        mDataLayer = dataLayer;
    }

    public Dispatcher getDispatcher() {
        return mDispatcher;
    }

    public DataLayer getDataLayer() {
        return mDataLayer;
    }
}

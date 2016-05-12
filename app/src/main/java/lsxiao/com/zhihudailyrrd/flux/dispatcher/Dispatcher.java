package lsxiao.com.zhihudailyrrd.flux.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;

/**
 * author lsxiao
 * date 2016-05-09 17:28
 * <p/>
 * 负责Action的分发，以及Store的订阅和取消订阅事件
 */
public class Dispatcher {
    private List<BaseStore> mStoreList;
    private static Dispatcher sInstance;

    private Dispatcher() {
        mStoreList = new ArrayList<>();
    }


    /**
     * 返回Dispatcher单例对象
     *
     * @return dispatcher Dispatcher
     */
    public static Dispatcher instance() {
        if (sInstance == null) {
            sInstance = new Dispatcher();
        }
        return sInstance;
    }

    /**
     * 订阅action
     *
     * @param store BaseStore
     */
    public void register(BaseStore store) {
        if (null == store) {
            throw new IllegalArgumentException("the store can't be null");
        }
        mStoreList.add(store);
    }

    /**
     * 订阅action
     *
     * @param stores BaseStore[]
     */
    public void register(BaseStore... stores) {
        if (null == stores || stores.length == 0) {
            throw new IllegalArgumentException("the store array is null or empty");
        }
        register(Arrays.asList(stores));
    }

    /**
     * 订阅action
     *
     * @param stores List<BaseStore>
     */
    public void register(List<BaseStore> stores) {
        if (null == stores || stores.isEmpty()) {
            throw new IllegalArgumentException("the store list is null or empty");
        }
        mStoreList.addAll(stores);
    }

    /**
     * 取消订阅action
     *
     * @param store BaseStore
     */
    public void unregister(BaseStore store) {
        if (null == store) {
            throw new IllegalArgumentException("the store can't be null");
        }
        mStoreList.remove(store);
    }

    /**
     * 取消订阅action
     *
     * @param stores BaseStore[]
     */
    public void unregister(BaseStore... stores) {
        if (null == stores || stores.length == 0) {
            throw new IllegalArgumentException("the store array is null or empty");
        }
        unregister(Arrays.asList(stores));
    }

    /**
     * 取消订阅action
     *
     * @param stores List<BaseStore>
     */
    public void unregister(List<BaseStore> stores) {
        if (null == stores || stores.isEmpty()) {
            throw new IllegalArgumentException("the store list is null or empty");
        }
        mStoreList.removeAll(stores);
    }

    /**
     * 分发action给特定的store
     *
     * @param action BaseAction child instance
     * @param store  BaseStore child instance
     */
    public void dispatch(BaseAction action, BaseStore store) {
        if (null == action || null == store) {
            throw new IllegalArgumentException("the action and store can't be null");
        }

        if (!mStoreList.contains(store)) {
            throw new IllegalArgumentException("the store can't be find,you must register the store to dispatcher first");
        }

        store.onAction(action);
    }

    /**
     * 分发action
     *
     * @param action BaseAction child instance
     */
    public void dispatch(BaseAction action) {
        if (null == action) {
            throw new IllegalArgumentException("the action can't be null");
        }
        performTraversals(action);
    }

    /**
     * 执行遍历
     * 把action分发给所有的store
     *
     * @param action BaseAction
     */
    private void performTraversals(BaseAction action) {
        for (BaseStore store : mStoreList) {
            store.onAction(action);
        }
    }
}

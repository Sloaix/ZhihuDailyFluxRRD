package lsxiao.com.zhihudailyrrd.flux.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;

/**
 * author lsxiao
 * date 2016-05-09 17:28
 */
public class Dispatcher {
    private List<BaseStore> mBaseStoreList;
    private static Dispatcher sInstance;

    private Dispatcher() {
        mBaseStoreList = new ArrayList<>();
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
        mBaseStoreList.add(store);
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
        mBaseStoreList.addAll(Arrays.asList(stores));
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
        register((BaseStore[]) stores.toArray());
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
        mBaseStoreList.remove(store);
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
        mBaseStoreList.removeAll(Arrays.asList(stores));
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
        unregister((BaseStore[]) stores.toArray());
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

        if (!mBaseStoreList.contains(store)) {
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
        for (BaseStore store : mBaseStoreList) {
            store.onAction(action);
        }
    }
}

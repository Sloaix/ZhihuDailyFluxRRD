package lsxiao.com.zhihudailyrrd.flux.dispatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;
import lsxiao.com.zhihudailyrrd.util.RxBus;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * author lsxiao
 * date 2016-05-09 17:28
 * <p>
 * 负责Action的分发，以及Store的订阅和取消订阅事件
 */
public class Dispatcher {
    private Map<BaseStore, Subscription> mStoreSubscriptionHashMap;
    private static Dispatcher sInstance;
    private Observable<BaseAction> mActionObservable;

    private Dispatcher() {
        mStoreSubscriptionHashMap = new HashMap<>();
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
    public void register(final BaseStore store) {
        if (null == store) {
            throw new IllegalArgumentException("the store can't be null");
        }

        Subscription subscription = Observable.defer(new Func0<Observable<BaseAction>>() {
            @Override
            public Observable<BaseAction> call() {
                return RxBus.instance().toObservable(BaseAction.class);
            }
        }).subscribe(new Action1<BaseAction>() {
            @Override
            public void call(BaseAction action) {
                store.onAction(action);
            }
        });
        mStoreSubscriptionHashMap.put(store, subscription);
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
        Observable.from(stores).forEach(new Action1<BaseStore>() {
            @Override
            public void call(BaseStore baseStore) {
                register(baseStore);
            }
        });
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

        //获取到订阅者
        Subscription subscription = mStoreSubscriptionHashMap.get(store);

        //如果没有取消订阅
        if (subscription != null && !subscription.isUnsubscribed()) {
            //取消订阅
            subscription.unsubscribe();
        }
        mStoreSubscriptionHashMap.remove(store);
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
        for (BaseStore store : stores) {
            unregister(store);
        }
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
        RxBus.instance().send(action);
    }

    /**
     * sticky 模式分发action
     *
     * @param action BaseAction
     */
    public void dispatchSticky(BaseAction action) {
        if (null == action) {
            throw new IllegalArgumentException("the action can't be null");
        }
        RxBus.instance().sendSticky(action);
    }
}

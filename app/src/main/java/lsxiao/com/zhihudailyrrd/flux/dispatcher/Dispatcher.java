package lsxiao.com.zhihudailyrrd.flux.dispatcher;

import android.util.Log;

import com.lsxiao.apllo.Apollo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * author lsxiao
 * date 2016-05-09 17:28
 * <p>
 * 负责Action的分发，以及Store的订阅和取消订阅事件
 */
public class Dispatcher {
    private Map<Integer, Subscription> mStoreSubscriptionHashMap;
    private static Dispatcher sInstance;
    private Observable<BaseAction> mActionObservable;
    private boolean mIsDispatching = false;

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

        final int uniqueId = System.identityHashCode(store);
        if (mStoreSubscriptionHashMap.containsKey(uniqueId)) {
            Subscription subscription = mStoreSubscriptionHashMap.get(uniqueId);
            if (subscription.isUnsubscribed()) {
                mStoreSubscriptionHashMap.remove(uniqueId);
            } else {
                return;
            }
        }

        //将subscription缓存下来,以便之后取消订阅
        mStoreSubscriptionHashMap.put(uniqueId, Apollo.get().toObservable(BaseAction.class.getCanonicalName(), BaseAction.class)
                .subscribe(new Action1<BaseAction>() {
                    @Override
                    public void call(BaseAction action) {
                        store.onAction(action);
                    }
                }));
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
        final int uniqueId = System.identityHashCode(store);
        //获取到订阅者
        Subscription subscription = mStoreSubscriptionHashMap.get(uniqueId);

        //如果没有取消订阅
        if (subscription != null && !subscription.isUnsubscribed()) {
            //取消订阅
            subscription.unsubscribe();
        }
        mStoreSubscriptionHashMap.remove(uniqueId);
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
        Apollo.get().send(BaseAction.class.getCanonicalName(), action);
    }
}

package lsxiao.com.zhihudailyrrd.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * author lsxiao
 * date 2016-05-09 17:27
 */
public class RxBus {
    private Subject publishSubject;
    private final Map<Class<?>, Object> mStickyEventMap;//用于保存stick事件
    private static RxBus sInstance;

    private RxBus() {
        //SerializedSubject是线程安全的
        //PublishSubject 会发送订阅者从订阅之后的事件序列,这意味着没订阅前的事件序列不会被发送到当前订阅者
        publishSubject = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    /**
     * 返回一个RxBus的单例对象
     *
     * @return RxBus
     */
    public synchronized static RxBus instance() {
        if (null == sInstance) {
            sInstance = new RxBus();
        }
        return sInstance;
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return sInstance.hasObservers();
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }


    /**
     * 发送普通事件
     *
     * @param o Object
     */
    public void send(Object o) {
        publishSubject.onNext(o);
    }

    /**
     * 发送一个新Sticky事件
     */
    public void sendSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        send(event);
    }

    /**
     * 返回普通事件类型的被观察者
     *
     * @param eventType 只接受eventType类型的响应,ofType = filter + cast
     * @return Observable
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return publishSubject.ofType(eventType);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            //普通事件的被观察者
            Observable<T> observable = toObservable(eventType);
            //sticky事件
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                //合并事件序列
                return Observable.merge(observable, Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }

}

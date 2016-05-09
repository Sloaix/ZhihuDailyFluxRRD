package lsxiao.com.zhihudailyrrd.util;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * author lsxiao
 * date 2016-05-09 17:27
 */
public class RxBus {
    private Subject subject;
    private static RxBus sInstance;

    private RxBus() {
        //SerializedSubject是线程安全的
        this.subject = new SerializedSubject<>(PublishSubject.create());
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
     * 发送事件
     *
     * @param o Object
     */
    public void send(Object o) {
        subject.onNext(o);
    }

    /**
     * @param eventType 只接受eventType类型的响应,ofType = filter + cast
     * @return Observable
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return subject.ofType(eventType);
    }
}

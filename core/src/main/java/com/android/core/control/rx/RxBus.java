package com.android.core.control.rx;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by _SOLID
 * Date:2016/6/1
 * Time:11:44
 */
public class RxBus {

    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> _bus;


    //PublishSubject只会把在订阅发生的时间点之后来自原始observable的数据发射给观察者
    private RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
    }

    //单例RxBus
    public static  RxBus getInstance() {
        if (defaultInstance == null)
        {
            synchronized (RxBus.class)
            {
                if (defaultInstance == null)
                {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    //发送一个新的事件
    public void post(Object o) {
        _bus.onNext(o);
    }

    //根据传递的eventType类型返回特定类型 eventType 的被观察者
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }
}

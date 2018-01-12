package com.yushilei.commonapp.common.proxy;

/**
 * @author shilei.yu
 * @since on 2018/1/12.
 */

public abstract class Observable<T, R> {
    private T data;

    public Observable(T data) {
        this.data = data;
    }

    public void subscribe(Observer<R> observer) {
        R real = apply(data);
        observer.onSubscribe(real);
    }

    public abstract R apply(T data);
}

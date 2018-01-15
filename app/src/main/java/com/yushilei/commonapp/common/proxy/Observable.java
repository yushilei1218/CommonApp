package com.yushilei.commonapp.common.proxy;

/**
 *
 */

public class Observable<T, R> {
    private T data;
    private Function<T, R> transfer;

    public Observable() {
    }

    public Observable(T data, Function<T, R> transfer) {
        this.data = data;
        this.transfer = transfer;
    }

    public void subscribe(Observer<R> observer) {
        R real = transfer.apply(data);
        observer.onSubscribe(real);
    }

    public Observable<T, R> subscribeOnIO() {
        return new ObservableIO<>(this);
    }

    public Observable<T, R> observeOnMain() {
        return new ObservableMain<>(this);
    }
}

package com.yushilei.commonapp.common.proxy;

import android.os.Handler;
import android.os.Looper;

/**
 * @author shilei.yu
 * @since on 2018/1/15.
 */

public class ObservableMain<T, R> extends Observable<T, R> {
    private Observable<T, R> real;

    public ObservableMain(Observable<T, R> real) {
        this.real = real;
    }

    @Override
    public void subscribe(Observer<R> observer) {
        real.subscribe(new MainObserver<>(observer));
    }

    public static final class MainObserver<R> implements Observer<R> {
        private final Observer<R> real;

        public MainObserver(Observer<R> real) {
            this.real = real;
        }

        @Override
        public void onSubscribe(final R data) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    real.onSubscribe(data);
                }
            });
        }
    }

}

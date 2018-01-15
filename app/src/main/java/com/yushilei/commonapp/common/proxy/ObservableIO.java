package com.yushilei.commonapp.common.proxy;

import com.yushilei.commonapp.common.async.ThreadPools;

/**
 * @author shilei.yu
 * @since on 2018/1/15.
 */

public class ObservableIO<T, R> extends Observable<T, R> {
    private Observable<T, R> real;

    public ObservableIO(Observable<T, R> real) {
        this.real = real;
    }

    @Override
    public void subscribe(final Observer<R> observer) {
        ThreadPools.newThread(new Runnable() {
            @Override
            public void run() {
                real.subscribe(observer);
            }
        }).start();
    }
}

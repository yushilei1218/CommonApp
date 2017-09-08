package com.yushilei.commonapp.common.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.schedulers.Schedulers;

/**
 * @author shilei.yu
 * @since 2017/9/1
 */

public class Observable<T> implements Runnable, IObservable<T> {

    private static final Handler sHandler;

    private static final int CODE_ERROR = 0x011;
    private static final int CODE_COMPLETE = 0x012;

    static {
        /*事物处理*/
        sHandler = new Handler(Looper.getMainLooper()) {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                Event event = (Event) msg.obj;
                Observer observer = event.mObservable.observer;
                switch (msg.what) {
                    case CODE_COMPLETE:
                        observer.onComplete(event.data);
                        break;
                    case CODE_ERROR:
                        observer.onComplete(event.throwable);
                        break;
                }
            }
        };
    }

    private final Task<T> mTask;
    private Observer<T> observer;

    public Observable(Task<T> task) {
        mTask = task;
    }

    @Override
    public void subscribe(Observer<T> observer) {
        this.observer = observer;
        /*
         * 运行时 切换线程的地方
         */
        ThreadPools.execute(this);
    }


    @Override
    public void run() {
        T data;
        try {
            data = mTask.task();
        } catch (Throwable throwable) {
            deliverySubscribeError(throwable);
            return;
        }
        /*
         *执行时 切换线程的地方
         */
        deliverySubscribeComplete(data);
    }

    private void deliverySubscribeError(Throwable throwable) {
        Message message = sHandler.obtainMessage(CODE_ERROR);
        message.obj = new Event<T>(this, throwable);
        sHandler.sendMessage(message);
    }

    private void deliverySubscribeComplete(T data) {
        Message message = sHandler.obtainMessage(CODE_COMPLETE);
        message.obj = new Event<T>(this, data);
        sHandler.sendMessage(message);
    }

    private static final class Event<T> {
        Observable<T> mObservable;
        T data;
        Throwable throwable;

        Event(Observable<T> observable, T data) {
            mObservable = observable;
            this.data = data;
        }

        Event(Observable<T> observable, Throwable throwable) {
            mObservable = observable;
            this.throwable = throwable;
        }
    }
}

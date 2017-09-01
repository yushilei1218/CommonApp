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

/**
 * @author shilei.yu
 * @since 2017/9/1
 */

public class Observable<T> implements Runnable {
    private static final Executor sExecutor;

    private static final Handler sHandler;

    private static final int CODE_ERROR = 0x011;
    private static final int CODE_COMPLETE = 0x012;


    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "Task Thread #" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);

    static {
        /*构造线程池*/
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        sExecutor = threadPoolExecutor;
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

    public void subscribe(Observer<T> observer) {
        this.observer = observer;
        sExecutor.execute(this);
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

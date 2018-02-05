package com.shileiyu.imageloader.net;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池封装
 *
 * @author shilei.yu
 * @since 2017/10/19
 */

public class ThreadPools {
    private ThreadPools() {
    }

    private static final Executor S_EXECUTOR;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ThreadFactory S_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "Task Thread #" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> S_POOL_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(128);

    static {
        /*构造线程池*/
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                S_POOL_WORK_QUEUE, S_THREAD_FACTORY);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        S_EXECUTOR = threadPoolExecutor;
    }

    public static void execute(Runnable runnable) {
        S_EXECUTOR.execute(runnable);
    }

    public static Thread newThread(Runnable run) {
        return S_THREAD_FACTORY.newThread(run);
    }
}

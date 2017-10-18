package com.yushilei.commonapp.common.async.scheduler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yushilei.commonapp.common.async.ThreadPools;

/**
 * @author shilei.yu
 * @since 2017/9/8
 */

public final class Schedulers {

    private static SchedulerMain sSchedulerMain;

    public static Scheduler io() {
        return new SchedulerIo();
    }

    public static synchronized Scheduler mainThread() {
        if (sSchedulerMain == null) {
            sSchedulerMain = new SchedulerMain();
        }
        return sSchedulerMain;
    }

    public static Scheduler newThread() {
        return new SchedulerNew();
    }

    private static final class SchedulerMain implements Scheduler {

        private final Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //处理主线程回调
            }
        };

        @Override
        public void execute(Runnable run) {
            mHandler.post(run);
        }

    }

    private static final class SchedulerNew implements Scheduler {

        @Override
        public void execute(Runnable run) {
            ThreadPools.newThread(run).start();
        }

    }

    private static final class SchedulerIo implements Scheduler {

        @Override
        public void execute(Runnable run) {
            ThreadPools.execute(run);
        }
    }
}

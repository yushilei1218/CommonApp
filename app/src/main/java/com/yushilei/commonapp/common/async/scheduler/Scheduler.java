package com.yushilei.commonapp.common.async.scheduler;

/**
 * @author shilei.yu
 * @since 2017/9/8
 */

public interface Scheduler {

    void execute(Runnable run);
}

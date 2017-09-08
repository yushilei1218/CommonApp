package com.yushilei.commonapp.common.async.scheduler;

/**
 * @author shilei.yu
 * @since 2017/9/8
 */

public interface Function<T, R> {
    R apply(T t) throws Throwable;
}

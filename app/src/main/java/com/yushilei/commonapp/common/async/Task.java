package com.yushilei.commonapp.common.async;

/**
 * @author shilei.yu
 * @since 2017/9/1
 */

public interface Task<T> {
    T task() throws Throwable;
}

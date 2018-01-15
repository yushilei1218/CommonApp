package com.yushilei.commonapp.common.proxy;

/**
 * @author shilei.yu
 * @since on 2018/1/15.
 */

public interface Function<T, R> {
    R apply(T t);
}

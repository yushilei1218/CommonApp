package com.yushilei.commonapp.common.proxy;

/**
 * @author shilei.yu
 * @since on 2018/1/12.
 */

public interface Observer<T> {

    void onSubscribe(T data);
}

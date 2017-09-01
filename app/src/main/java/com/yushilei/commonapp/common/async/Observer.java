package com.yushilei.commonapp.common.async;

/**
 * @author shilei.yu
 * @since 2017/9/1
 */

public interface Observer<T> {
    void onComplete(T data);

    void onError(Throwable trx);
}

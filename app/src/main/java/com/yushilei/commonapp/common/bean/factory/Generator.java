package com.yushilei.commonapp.common.bean.factory;

/**
 * @author shilei.yu
 * @since on 2018/3/8.
 */

public interface Generator<T> {
    T next();
}

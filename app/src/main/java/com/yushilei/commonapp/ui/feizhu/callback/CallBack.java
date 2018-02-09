package com.yushilei.commonapp.ui.feizhu.callback;

/**
 * @author shilei.yu
 * @since on 2018/2/9.
 */

public interface CallBack<T> {
    void callBack(T data);

    void onFailed(String msg, int code);
}

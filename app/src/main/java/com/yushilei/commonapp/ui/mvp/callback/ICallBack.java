package com.yushilei.commonapp.ui.mvp.callback;

/**
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public interface ICallBack<T> {
    void onSuccess(T data);

    void onFail(String msg);
}

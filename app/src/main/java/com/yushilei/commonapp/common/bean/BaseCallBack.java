package com.yushilei.commonapp.common.bean;

import com.google.gson.reflect.TypeToken;

/**
 * @author shilei.yu
 * @since on 2018/3/14.
 */

public abstract class BaseCallBack<T> {

    public TypeToken<HttpResponse<T>> mToken;

    public BaseCallBack(TypeToken<HttpResponse<T>> token) {
        mToken = token;
    }

    public abstract void onSuccess(HttpResponse<T> data);

    public abstract void onFail(int code, String msg);
}

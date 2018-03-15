package com.yushilei.commonapp.common.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author shilei.yu
 * @since on 2018/3/15.
 */

public abstract class CallBack<T> {
    public final TypeToken<T> mToken;

    public CallBack(TypeToken<T> token) {
        mToken = token;
    }

    public abstract void onSuccess(T t);

    public static void main(String[] args) {
        CallBack<HttpResponse<BeanA>> callBack = new CallBack<HttpResponse<BeanA>>(new TypeToken<HttpResponse<BeanA>>() {
        }) {
            @Override
            public void onSuccess(HttpResponse<BeanA> response) {

            }
        };
        String json="";
        HttpResponse<BeanA> o = new Gson().fromJson(json, callBack.mToken.getType());
    }
}

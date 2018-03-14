package com.yushilei.commonapp.common.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yushilei.commonapp.common.util.JsonUtil;

/**
 * @author shilei.yu
 * @since on 2018/3/14.
 */

public class Procesor {
    public static <T> void call(BaseCallBack<T> base) {
        String json = "{\"code\":10,\"data\":{\"name\":\"test\"}}";
        HttpResponse<T> a = getT(base.mToken, json);
        base.onSuccess(a);

    }

    public static void main(String[] args) {

        BeanA beanA = new BeanA();
        beanA.name = "test";
        HttpResponse<BeanA> bean = new HttpResponse<>(10, beanA);
        String json = JsonUtil.toJson(bean);
        System.out.println(json);

        TypeToken<HttpResponse<BeanA>> token = new TypeToken<HttpResponse<BeanA>>() {
        };
        HttpResponse<BeanA> testBean = getT(token, json);

        BaseCallBack<BeanA> callBack = new BaseCallBack<BeanA>(new TypeToken<HttpResponse<BeanA>>() {
        }) {
            @Override
            public void onSuccess(HttpResponse<BeanA> response) {
                BeanA data = response.getData();
                int code = response.getCode();
                System.out.println("BeanA= " + data + " code=" + code);
            }

            @Override
            public void onFail(int code, String msg) {

            }
        };
        callBack.onSuccess(testBean);

        call(callBack);
    }

    public static <T> T getT(TypeToken<T> token, String json) {
        java.lang.reflect.Type type = token.getType();
        return new Gson().fromJson(json, type);
    }
}

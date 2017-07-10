package com.yushilei.commonapp.common.net;

import retrofit2.Retrofit;

/**
 * Retrofit2 网络封装
 *
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public class NetApi {
    public static API api;

    static {
        Retrofit retrofit = new Retrofit.Builder().build();
        api = retrofit.create(API.class);
    }

    public interface API {

    }
}

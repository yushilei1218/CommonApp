package com.yushilei.commonapp.common.net;

import com.yushilei.commonapp.common.bean.net.Recommend;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Retrofit2 网络封装
 *
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public class NetApi {
    public static API api;
    private static final String BASE_URL = "http://mobile.ximalaya.com";

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .client(Client.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        api = retrofit.create(API.class);
    }

    public interface API {
        @GET("/mobile/discovery/v3/recommend/ts-1500624532898")
        Call<Recommend> getRecommend();
    }
}

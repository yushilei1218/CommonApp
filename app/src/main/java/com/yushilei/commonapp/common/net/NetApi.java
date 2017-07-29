package com.yushilei.commonapp.common.net;

import com.shileiyu.RetrofitProxy;
import com.yushilei.commonapp.common.bean.net.DiscoveryBean;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.net.encrypt.EncryptAnnotation;
import com.yushilei.commonapp.common.net.encrypt.EncryptConverterFactory;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
                /*设置指定的OkHttpClient*/
                .client(Client.getClient())
                /*Json数据加密 解密*/
                .addConverterFactory(EncryptConverterFactory.create())
                /*Json序列化、反序列化*/
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        api = retrofit.create(API.class);
    }

    /**
     * 网络请求API
     * <p>
     * {@link RetrofitProxy} 自定义注解，使用编译时注解自动生成网络请求代理类
     * {@link EncryptAnnotation}自定义注解，对需要的接口请求做加密和解密操作
     */
    @RetrofitProxy
    public interface API {
        @EncryptAnnotation
        @GET("/mobile/discovery/v3/recommend/ts-1500624532898")
        Call<DiscoveryBean> getDiscovery();

        @GET("/mobile/discovery/v1/recommend/albums")
        Call<Recommend> getRecommend(@Query("pageId") int pageId, @Query("pageSize") int pageSize);
    }
}

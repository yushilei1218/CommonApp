package com.yushilei.commonapp.common.net;

import com.google.gson.reflect.TypeToken;
import com.shileiyu.RetrofitProxy;
import com.yushilei.commonapp.common.bean.net.DiscoveryBean;
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.bean.net.YouLike;
import com.yushilei.commonapp.common.net.encrypt.EncryptAnnotation;
import com.yushilei.commonapp.common.net.encrypt.EncryptConverterFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
    public static FlowApi sFlowapi;
    public static API2 sApi2;

    private static final String BASE_URL = "http://mobile.ximalaya.com";
    private static final String BASE_URL2 = "http://mobwsa.ximalaya.com";

    static {
        Retrofit retrofit = new Retrofit.Builder()
                /*设置指定的OkHttpClient*/
                .client(Client.getClient())
                /*Json数据加密 解密*/
                .addConverterFactory(EncryptConverterFactory.create())
                /*Json序列化、反序列化*/
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        api = retrofit.create(API.class);
        sFlowapi = retrofit.create(FlowApi.class);

        Retrofit retrofit2 = new Retrofit.Builder()
                /*设置指定的OkHttpClient*/
                .client(Client.getClient())
                /*Json数据加密 解密*/
                .addConverterFactory(EncryptConverterFactory.create())
                /*Json序列化、反序列化*/
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .baseUrl(BASE_URL2)
                .build();
        sApi2 = retrofit2.create(API2.class);
    }


    /**
     * 网络请求API
     * <p>
     * {@link RetrofitProxy} 自定义注解，使用编译时注解自动生成网络请求代理类
     * {@link EncryptAnnotation}自定义注解，对需要的接口请求做加密和解密操作
     */
    @RetrofitProxy
    public interface API {

        @GET("/mobile/discovery/v3/recommend/ts-1500624532898")
        Call<DiscoveryBean> getDiscovery();

        @GET("/mobile/discovery/v1/recommend/albums")
        Call<RecommendBean> getRecommend(@Query("pageId") int pageId, @Query("pageSize") int pageSize);

    }

    public interface API2 {
        @GET("/discovery-firstpage/guessYouLike/list/ts-1516156034599")
        Call<YouLike> getYouLike(@Query("pageId") int pageId, @Query("pageSize") int pageSize);

        @GET("/discovery-firstpage/guessYouLike/list/ts-1516156034599")
        Observable<YouLike> getRxYouLike(@Query("pageId") int pageId, @Query("pageSize") int pageSize);
    }

    public interface FlowApi {
        @GET("/mobile/discovery/v1/recommend/albums")
        Flowable<RecommendBean> getFlowRecommend(@Query("pageId") int pageId, @Query("pageSize") int pageSize);
    }
}

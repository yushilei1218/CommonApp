package com.yushilei.commonapp.common.net;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公共OkHttpClient 获取辅助类
 *
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class Client {
    private Client() {
    }

    private static OkHttpClient client;

    public static synchronized OkHttpClient getClient() {
        if (client == null) {
           /* InputStream ins; 获取CA证书 读取流
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(ins, null, null);*/

            client = new OkHttpClient.Builder()
                    .writeTimeout(45, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    /*拦截器 -支持 公共参数、Header添加*/
                    .addInterceptor(new CommonInterceptor())
                    /*CA证书*/
                    /* .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)*/
                    .build();
        }
        return client;
    }

    /**
     * 拦截器
     * <p>
     * 为公共参数、Header添加提供支持
     */
    private static final class CommonInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request oldRequest = chain.request();
            HttpUrl.Builder builder = oldRequest.url().newBuilder();
                    /*公共参数的添加*/
            builder.addQueryParameter("device", "android")
                    .addQueryParameter("deviceId", "ffffffff-d31d-8252-ffff-ffff97b77fee")
                    .addQueryParameter("version", "6.3.9")
                    .addQueryParameter("code", "43_110000_1100")
                    .addQueryParameter("includeActivity", "true")
                    .addQueryParameter("includeSpecial", "true")
                    .addQueryParameter("categoryId", "-2")
                    .addQueryParameter("inreview", "false")
                    .addQueryParameter("network", "wifi");
                    /*公共Header的添加*/
            Request.Builder requestBuilder = oldRequest.newBuilder()
                    .addHeader("user-agent", "ting_6.3.9(MI+NOTE+LTE,Android19)");

            Request newRequest = requestBuilder.url(builder.build()).build();

            return chain.proceed(newRequest);
        }
    }
}

package com.yushilei.commonapp.common.net;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class Client {
    private static OkHttpClient client;

    public static synchronized OkHttpClient getClient() {
        if (client == null)
            client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request oldRequest = chain.request();
                    HttpUrl.Builder builder = oldRequest.url().newBuilder();

                    builder.addQueryParameter("device", "android")
                            .addQueryParameter("deviceId", "ffffffff-d31d-8252-ffff-ffff97b77fee")
                            .addQueryParameter("version", "6.3.9")
                            .addQueryParameter("code", "43_110000_1100")
                            .addQueryParameter("includeActivity", "true")
                            .addQueryParameter("includeSpecial", "true")
                            .addQueryParameter("categoryId", "-2")
                            .addQueryParameter("inreview", "false")
                            .addQueryParameter("network", "wifi");

                    Request.Builder requestBuilder = oldRequest.newBuilder()
                            .addHeader("user-agent", "ting_6.3.9(MI+NOTE+LTE,Android19)");

                    Request newRequest = requestBuilder.url(builder.build()).build();

                    return chain.proceed(newRequest);
                }
            }).build();
        return client;
    }
}

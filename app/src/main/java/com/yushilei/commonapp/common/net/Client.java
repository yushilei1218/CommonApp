package com.yushilei.commonapp.common.net;

import android.support.annotation.NonNull;

import java.io.IOException;

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
                    Request request = chain.request();
                    String url = request.url().toString();
                    return chain.proceed(request);
                }
            }).build();
        return client;
    }
}

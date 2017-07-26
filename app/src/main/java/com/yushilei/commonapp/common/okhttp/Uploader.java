package com.yushilei.commonapp.common.okhttp;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.net.Client;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author shilei.yu
 * @since on 2017/7/26.
 */

public class Uploader {
    public static Call startUpload(String url, File file, Callback callback, ProgressRequestListener listener) {
        OkHttpClient client = Client.getClient();

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("thumb", file.getName(),
                RequestBody.create(MediaType.parse("image/*"), file));

        ProgressRequestBody body = new ProgressRequestBody(builder.build(), listener);

        Request request = new Request.Builder().post(body)
                .url(url)
                .build();

        Call uploadCall = client.newCall(request);

        uploadCall.enqueue(callback);
        return uploadCall;
    }

    public static Call startUpload(String url, File file, Callback callback) {
        return startUpload(url, file, callback, null);
    }
}

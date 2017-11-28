package com.yushilei.commonapp.ui.weex.adapter;

import android.support.annotation.NonNull;

import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.yushilei.commonapp.common.net.Client;
import com.yushilei.commonapp.common.util.SetUtil;


import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author shilei.yu
 * @since 2017/11/28
 */

public class OkHttpAdapter implements IWXHttpAdapter {
    @Override
    public void sendRequest(WXRequest request, OnHttpListener listener) {
        Request okRequest = getOkRequest(request);
        Client.getClient().newCall(okRequest).enqueue(new CallBackWrapper(listener));
    }

    private Request getOkRequest(WXRequest request) {
        Request.Builder builder = new Request.Builder();
        String method = request.method.toUpperCase();
        builder.url(request.url);
        if ("GET".equals(method)) {
            builder.get();
        } else if ("POST".equals(method)) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), request.body);
            builder.post(body);
        }
        Map<String, String> map = request.paramMap;
        if (!SetUtil.isEmpty(map)) {
            for (String key : map.keySet()) {
                builder.addHeader(key, map.get(key));
            }
        }
        return builder.build();
    }

    private static final class CallBackWrapper implements Callback {
        private final OnHttpListener mHttpListener;

        CallBackWrapper(@NonNull OnHttpListener httpListener) {
            mHttpListener = httpListener;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            WXResponse response = new WXResponse();
            response.data = null;
            response.errorCode = null;
            response.errorMsg = e.getMessage();
            response.statusCode = null;
            mHttpListener.onHttpFinish(response);
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            mHttpListener.onHeadersReceived(response.code(), response.headers().toMultimap());
            WXResponse wxResponse = new WXResponse();
            ResponseBody body = response.body();
            wxResponse.statusCode = String.valueOf(response.code());
            if (body != null) {
                wxResponse.data = body.string();
                wxResponse.originalData = body.bytes();
            }
            mHttpListener.onHttpFinish(wxResponse);
        }
    }
}

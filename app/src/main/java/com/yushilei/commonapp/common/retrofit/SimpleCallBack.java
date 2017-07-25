package com.yushilei.commonapp.common.retrofit;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SimpleCallBack 处理API 请求回调,使用该CallBack 业务层需要处理全部response code
 * <p>
 * 如果不处理公共response code，请使用{@link CommonCallBack}
 *
 * @author shilei.
 * @since on 2017/7/25.
 */

public class SimpleCallBack<T> extends BaseCallBack<T> {
    private Callback<T> callback;

    public SimpleCallBack(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        super.onResponse(call, response);
        callback.onResponse(call, response);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        super.onFailure(call, t);
        callback.onFailure(call, t);
    }
}

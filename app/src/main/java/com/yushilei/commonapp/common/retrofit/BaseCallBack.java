package com.yushilei.commonapp.common.retrofit;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CallBack 基类 ,负责在回调中移除 {@link CallPool} 中的Call
 *
 * @author shilei.yu
 * @since on 2017/7/25.
 */

@SuppressWarnings("WeakerAccess")
public abstract class BaseCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        CallPool.removeCall(call);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        CallPool.removeCall(call);
    }
}

package com.yushilei.commonapp.common.retrofit.Interceptor;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author shilei.yu
 * @since on 2017/7/26.
 */

public interface CallBackInterceptor<T> {
    boolean onResponse(@NonNull Call<T> call, @NonNull Response<T> response);

    boolean onFailure(@NonNull Call<T> call, @NonNull Throwable t);
}

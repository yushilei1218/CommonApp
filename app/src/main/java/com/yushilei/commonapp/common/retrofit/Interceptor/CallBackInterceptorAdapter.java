package com.yushilei.commonapp.common.retrofit.Interceptor;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Response;

/**
 * CallBackInterceptor 默认实现类
 * <p>
 * 简化CallBackInterceptor，旨在解决业务层 仅关心onResponse() or onFailure();
 * 关心那部分重写那部分,思想参见系统
 * {@link android.support.design.widget.AnimationUtils.AnimationListenerAdapter}
 *
 * @author shilei.yu
 * @since on 2017/7/26.
 */

public class CallBackInterceptorAdapter<T> implements CallBackInterceptor<T> {
    @Override
    public boolean onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        return false;
    }

    @Override
    public boolean onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        return false;
    }
}

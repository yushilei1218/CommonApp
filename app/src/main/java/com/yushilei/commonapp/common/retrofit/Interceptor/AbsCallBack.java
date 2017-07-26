package com.yushilei.commonapp.common.retrofit.Interceptor;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * CallBack抽象类，旨在解决业务层 明确关系的异常问题
 *
 * @author shilei.yu
 * @since on 2017/7/26.
 */

public abstract class AbsCallBack<T> implements Callback<T> {
    /**
     * @return true：表示业务关心该异常，并已经自行处理，false：业务层不关心，由公共层处理
     */
    public boolean onTimeOutException(@NonNull Call<T> call, @NonNull Throwable t) {
        return false;
    }
}

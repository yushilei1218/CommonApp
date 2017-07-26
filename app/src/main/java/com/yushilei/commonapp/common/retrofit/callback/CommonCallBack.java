package com.yushilei.commonapp.common.retrofit.callback;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.retrofit.IhrException;
import com.yushilei.commonapp.common.retrofit.Interceptor.CallBackInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * CommonCallBack 为处理网络请求公共CallBack ,内部处理API 公共事件
 * <p>
 * 如果需要业务层自己处理所有网络code ，请使用{@link SimpleCallBack}
 *
 * @author shilei.yu
 * @since on 2017/7/25.
 */

public class CommonCallBack<T> extends BaseCallBack<T> {

    private AbsCallBack<T> callback;

    private CallBackInterceptor<T> mInterceptor;

    /**
     * 构造函数
     */
    public CommonCallBack(AbsCallBack<T> callback) {
        this.callback = callback;
    }

    /**
     * 增加拦截器构造函数
     */
    public CommonCallBack(AbsCallBack<T> callback, CallBackInterceptor<T> interceptor) {
        this.callback = callback;
        this.mInterceptor = interceptor;
    }

    public void setInterceptor(CallBackInterceptor<T> interceptor) {
        this.mInterceptor = interceptor;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        super.onResponse(call, response);

        if (mInterceptor != null && mInterceptor.onResponse(call, response))
            return;

        /*这个地方可以处理API公共逻辑*/
        if (response.isSuccessful()) {

            callback.onResponse(call, response);

        } else if (response.code() >= 1000 && response.code() <= 1010) {
            /*账号异常，请重新登录*/
            callback.onFailure(call, new IhrException("账号冻结"));
            /*处理公共逻辑todo*/
        } else if (response.code() == 404) {
            callback.onFailure(call, new IhrException("服务器未找到资源"));
        } else {
            callback.onFailure(call, new IhrException("未知原因"));
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        super.onFailure(call, t);
        /*如果拦截器关心并处理了该异常，则流程结束，否则交给公共逻辑*/
        if (mInterceptor != null && mInterceptor.onFailure(call, t))
            return;

        /*如果callBack关心并处理了TimeOutEx，则流程结束，否则交给公共逻辑*/
        if (t instanceof TimeoutException && callback.onTimeOutException(call, t))
            return;

        if (t instanceof IOException && t.getMessage().equals("Canceled")) {
            /*公共逻辑处理,此时为页面消耗主动cancel 抛出的异常 egonDestroy时会将view置null:,避免view空指针*/
            t.printStackTrace();
            return;
        }
        /*否则交给业务层处理异常*/
        callback.onFailure(call, t);
    }
}

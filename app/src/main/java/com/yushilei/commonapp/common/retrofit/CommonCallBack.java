package com.yushilei.commonapp.common.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
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
    private Callback<T> callback;

    public CommonCallBack(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        super.onResponse(call, response);
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
        if (t instanceof IOException && t.getMessage().equals("Canceled")) {
            /*公共逻辑处理,此时为页面消耗主动cancel 抛出的异常*/
            /*异常的cancel也是异步，避免 callback.onFailure(call, t)引发空指针 ,
            因Presenter onDestroy时会将view置null*/
            t.printStackTrace();
        } else {
            callback.onFailure(call, t);
        }
    }
}

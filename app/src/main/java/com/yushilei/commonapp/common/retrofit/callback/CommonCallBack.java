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
 * 如果需要业务层自己处理所有网络code和异常，请使用{@link SimpleCallBack}
 * <p>
 * 优化：CommonCallBack 抽象化，移除不必要的callBack ，由CommonCallBack 抽象方法替代；
 * 子类方法需要重写
 * {@link #onBizResponse(Call, Response)}、
 * {@link #onBizFailure(Call, Throwable)}处理业务回调；
 *
 * @author shilei.yu
 * @since on 2017/7/25.
 */

public abstract class CommonCallBack<T> extends BaseCallBack<T> {

    private CallBackInterceptor<T> mInterceptor;

    public CommonCallBack() {
    }

    /**
     * 增加拦截器构造函数
     */
    public CommonCallBack(CallBackInterceptor<T> interceptor) {
        this.mInterceptor = interceptor;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        super.onResponse(call, response);

        if (mInterceptor != null && mInterceptor.onResponse(call, response))
            return;

        /*这个地方可以处理API公共逻辑*/
        if (response.isSuccessful()) {

            onBizResponse(call, response);

        } else if (response.code() >= 1000 && response.code() <= 1010) {
            /*账号异常，请重新登录*/
            onBizFailure(call, new IhrException("账号冻结"));
            /*处理公共逻辑todo*/
        } else if (response.code() == 404) {
            onBizFailure(call, new IhrException("服务器未找到资源"));
        } else {
            onBizFailure(call, new IhrException("未知原因"));
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        super.onFailure(call, t);
        /*如果拦截器关心并处理了该异常，则流程结束，否则交给公共逻辑*/
        if (mInterceptor != null && mInterceptor.onFailure(call, t))
            return;

        /*如果callBack关心并处理了TimeOutEx，则流程结束，否则交给公共逻辑*/
        if (t instanceof TimeoutException && onTimeOutException(call, t))
            return;

        if (t instanceof IOException && t.getMessage().equals("Canceled")) {
            /*2种情况
            * 1.主动cancel Call,既然是业务主动cancel,那就应该立刻维护自行维护view状态
            * 2.onDestroy 公共组件cancel taskId关联的所有Call 页面已经消耗了也不必再传业务层了*/
            t.printStackTrace();
            return;
        }
        /*否则交给业务层处理异常*/
        onBizFailure(call, t);
    }

    public abstract void onBizResponse(@NonNull Call<T> call, @NonNull Response<T> response);

    public abstract void onBizFailure(@NonNull Call<T> call, @NonNull Throwable t);

    /**
     * 提供默认实现的TimeOutException 方法，业务层如果关心该异常可以自行在子类中重写该方法
     *
     * @param call {@link Call}
     * @param t    {@link TimeoutException}
     * @return true:代表拦截TimeOutException 并已经自行处理，false：不拦截不处理
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean onTimeOutException(@NonNull Call<T> call, @NonNull Throwable t) {
        return false;
    }
}

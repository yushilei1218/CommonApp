package com.yushilei.commonapp.common.retrofit;

import com.yushilei.commonapp.common.bean.net.Discovery;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.net.NetApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Query;

/**
 * API 接口代理类
 * <p>
 * 将每次创建的Call 自动加入的{@link CallPool} 中
 * <p>
 * 在调用{@link Call#enqueue(Callback)} 方法时，
 * callback请使用{@link CommonCallBack} or {@link SimpleCallBack}
 *
 * @author shilei.yu
 * @since on 2017/7/25.
 */

public abstract class NetProxy implements NetApi.API {
    @Override
    public Call<Discovery> getDiscovery() {
        Call<Discovery> call = NetApi.api.getDiscovery();
        addCall(call);
        return call;
    }

    @Override
    public Call<Recommend> getRecommend(@Query("pageId") int pageId, @Query("pageSize") int pageSize) {
        Call<Recommend> call = NetApi.api.getRecommend(pageId, pageSize);
        addCall(call);
        return call;
    }

    private void addCall(Call call) {
        CallPool.addCall(call, getTaskId());
    }

    /**
     * 子类需要重写该方法：
     * 获取任务ID ,一个任务ID 可能与多个{@link Call} 关联；
     * 这里的任务ID 一般是 MVP中 V的hashCode
     *
     * @return taskId
     * @see com.yushilei.commonapp.common.mvp.BasePresenter#mTaskId
     */
    public abstract int getTaskId();
}

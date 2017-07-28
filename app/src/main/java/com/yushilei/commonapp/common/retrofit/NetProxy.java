package com.yushilei.commonapp.common.retrofit;

import com.yushilei.commonapp.common.bean.net.Discovery;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.net.NetApi;
import com.yushilei.commonapp.common.retrofit.callback.CommonCallBack;
import com.yushilei.commonapp.common.retrofit.callback.SimpleCallBack;

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

public class NetProxy implements NetApi.API {
    private int mTaskId;

    public NetProxy(int mTaskId) {
        this.mTaskId = mTaskId;
    }

    @Override
    public Call<Discovery> getDiscovery() {
        Call<Discovery> call = NetApi.api.getDiscovery();
        addCall(call);
        return call;
    }

    @Override
    public Call<Recommend> getRecommend(int pageId, int pageSize) {
        Call<Recommend> call = NetApi.api.getRecommend(pageId, pageSize);
        addCall(call);
        return call;
    }

    private void addCall(Call call) {
        CallPool.addCall(call, mTaskId);
    }
}

package com.yushilei.commonapp.common.retrofit;

import com.yushilei.commonapp.common.bean.net.DiscoveryBean;
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.net.NetApi;
import com.yushilei.commonapp.common.retrofit.callback.CommonCallBack;
import com.yushilei.commonapp.common.retrofit.callback.SimpleCallBack;

import io.reactivex.Flowable;
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
    public Call<DiscoveryBean> getDiscovery() {
        Call<DiscoveryBean> call = NetApi.api.getDiscovery();
        addCall(call);
        return call;
    }

    @Override
    public Call<RecommendBean> getRecommend(int pageId, int pageSize) {
        Call<RecommendBean> call = NetApi.api.getRecommend(pageId, pageSize);
        addCall(call);
        return call;
    }

    private void addCall(Call call) {
        CallPool.addCall(call, mTaskId);
    }
}

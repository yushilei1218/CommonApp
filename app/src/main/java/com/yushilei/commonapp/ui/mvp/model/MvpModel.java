package com.yushilei.commonapp.ui.mvp.model;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.net.NetApi;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.ui.mvp.callback.ICallBack;
import com.yushilei.commonapp.ui.mvp.contract.MvpContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public class MvpModel extends MvpContract.BaseModel {
    private int index = 1;

    public MvpModel(int taskId) {
        super(taskId);
    }

    @Override
    public void refresh(final ICallBack<List<Data>> callBack) {
        index = 1;
        NetApi.api.getRecommend(index, 20).enqueue(new Callback<RecommendBean>() {
            @Override
            public void onResponse(@NonNull Call<RecommendBean> call, @NonNull Response<RecommendBean> response) {
                RecommendBean body = response.body();
                if (body != null && !SetUtil.isEmpty(body.getList())) {
                    data.clear();
                    data.addAll(body.getList());
                }

                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(@NonNull Call<RecommendBean> call, Throwable t) {

                callBack.onFail("网络异常啊啊");
            }
        });
    }

    @Override
    public void loadMore(final ICallBack<List<Data>> callBack) {
        index++;
        NetApi.api.getRecommend(index, 20).enqueue(new Callback<RecommendBean>() {
            @Override
            public void onResponse(@NonNull Call<RecommendBean> call, @NonNull Response<RecommendBean> response) {
                RecommendBean body = response.body();
                if (body != null && !SetUtil.isEmpty(body.getList())) {
                    data.addAll(body.getList());
                }
                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(@NonNull Call<RecommendBean> call, Throwable t) {
                callBack.onFail("网络异常啊啊");
            }
        });
    }
}

package com.yushilei.commonapp.ui.mvp.model;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.bean.net.Album;
import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.bean.net.YouLike;
import com.yushilei.commonapp.common.net.NetApi;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.ui.mvp.callback.ICallBack;
import com.yushilei.commonapp.ui.mvp.contract.MvpContract;

import java.util.List;

import io.reactivex.Observable;
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
    public void refresh(final ICallBack<List<Album>> callBack) {
        index = 1;
        NetApi.sApi2.getYouLike(index, 20).enqueue(new Callback<YouLike>() {
            @Override
            public void onResponse(@NonNull Call<YouLike> call, @NonNull Response<YouLike> response) {
                YouLike body = response.body();
                if (body != null && !SetUtil.isEmpty(body.getList())) {
                    data.clear();
                    data.addAll(body.getList());
                }

                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(@NonNull Call<YouLike> call, Throwable t) {

                callBack.onFail("网络异常啊啊");
            }
        });
    }

    @Override
    public void loadMore(final ICallBack<List<Album>> callBack) {
        index++;
        NetApi.sApi2.getYouLike(index, 20).enqueue(new Callback<YouLike>() {
            @Override
            public void onResponse(@NonNull Call<YouLike> call, @NonNull Response<YouLike> response) {
                YouLike body = response.body();
                if (body != null && !SetUtil.isEmpty(body.getList())) {
                    data.addAll(body.getList());
                }
                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(@NonNull Call<YouLike> call, Throwable t) {
                callBack.onFail("网络异常啊啊");
            }
        });
    }

    @Override
    public Observable<YouLike> refresh() {
        index = 1;
        return NetApi.sApi2.getRxYouLike(index, 20);
    }

    @Override
    public Observable<YouLike> loadMore() {
        return NetApi.sApi2.getRxYouLike(index, 20);
    }
}

package com.yushilei.commonapp.ui.mvp.model;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.bean.basedata.Job;
import com.yushilei.commonapp.common.bean.net.Album;
import com.yushilei.commonapp.common.bean.net.YouLike;
import com.yushilei.commonapp.common.net.NetApi;
import com.yushilei.commonapp.common.net.callback.BaseObserver;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.ui.mvp.callback.BooleanCallBack;
import com.yushilei.commonapp.ui.mvp.callback.ICallBack;
import com.yushilei.commonapp.ui.mvp.contract.MvpContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public class MvpModel extends MvpContract.BaseModel {
    private int index = 1;

    public MvpModel() {
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

                callBack.onFail(data, 1, "网络异常啊啊");
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
                callBack.onFail(data, 1, "网络异常啊啊");
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

    @Override
    public void refreshRx(final ICallBack<List<Album>> callBack) {
        NetApi.sApi2.getRxYouLike(1, 20)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<YouLike>(taskId) {
                    @Override
                    public void onSuccess(@NonNull YouLike youLike) {
                        data.clear();
                        if (!SetUtil.isEmpty(youLike.getList())) {
                            data.addAll(youLike.getList());
                        }
                        callBack.onSuccess(data);
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        callBack.onFail(data, code, msg);
                    }
                });
    }

    @Override
    public void refreshJobs(ICallBack<List<Job>> callBack) {

    }

    @Override
    public void loadMoreJobs(ICallBack<List<Job>> callBack) {

    }

    @Override
    public void checkUserState(BooleanCallBack callBack) {
        isUserCheckPassed = true;
        callBack.callBack(isUserCheckPassed);
    }
}

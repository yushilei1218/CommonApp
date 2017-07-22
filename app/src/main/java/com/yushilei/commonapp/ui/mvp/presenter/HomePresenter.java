package com.yushilei.commonapp.ui.mvp.presenter;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.net.NetApi;
import com.yushilei.commonapp.ui.mvp.contract.HomeContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class HomePresenter extends BasePresenter<HomeContract.IView> implements HomeContract.Presenter {
    public HomePresenter(HomeContract.IView view) {
        super(view);
    }

    private HomeContract.IModel model;

    public HomePresenter(HomeContract.IView view, HomeContract.IModel model) {
        super(view);
        this.model = model;
    }

    @Override
    public void beginRefreshData(boolean is) {
        Call<Recommend> call = NetApi.api.getRecommend();
        mView.showLoading();
        mView.onRefreshing();
        call.enqueue(new Callback<Recommend>() {
            @Override
            public void onResponse(@NonNull Call<Recommend> call, @NonNull Response<Recommend> response) {
                mView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    List<ItemWrapper> items = model.obtainItems(response.body());
                    mView.onRefreshFinish(items);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recommend> call, @NonNull Throwable t) {
                mView.hideLoading();
                mView.onRefreshFinish(null);
                t.printStackTrace();
                mView.showToast("网络异常");
            }
        });
    }
}

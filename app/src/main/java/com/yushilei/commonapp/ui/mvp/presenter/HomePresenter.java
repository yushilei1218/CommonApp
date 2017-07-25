package com.yushilei.commonapp.ui.mvp.presenter;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.Discovery;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.retrofit.CommonCallBack;
import com.yushilei.commonapp.common.retrofit.NetProxy;
import com.yushilei.commonapp.common.retrofit.SimpleCallBack;
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

    public HomePresenter(HomeContract.IView view, HomeContract.IModel model) {
        super(view);
        this.model = model;
    }

    private HomeContract.IModel model;

    private NetProxy mNetProxy = new NetProxy() {
        @Override
        public int getTaskId() {
            return mTaskId;
        }
    };

    @Override
    public void beginRefreshData(final boolean isByRefresh) {
        if (isByRefresh) {//用户触发刷新
            mView.onRefreshing();
        } else {//App加载触发刷新
            mView.showLoading();
        }
        // Call<Discovery> call = NetApi.api.getDiscovery();
        Call<Discovery> call = mNetProxy.getDiscovery();
        call.enqueue(new CommonCallBack<Discovery>(new Callback<Discovery>() {
            @Override
            public void onResponse(@NonNull Call<Discovery> call, @NonNull Response<Discovery> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ItemWrapper> items = model.obtainItems(response.body());
                    mView.onRefreshFinish(isByRefresh, true, items);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Discovery> call, @NonNull Throwable t) {
                t.printStackTrace();
                mView.onRefreshFinish(isByRefresh, false, null);
            }
        }));
    }

    @Override
    public void beginLoadMore() {
        // Call<Recommend> call = NetApi.api.getRecommend(model.getPageId(), model.getPageSize());
        Call<Recommend> call = mNetProxy.getRecommend(model.getPageId(), model.getPageSize());
        call.enqueue(new SimpleCallBack<Recommend>(new Callback<Recommend>() {
            @Override
            public void onResponse(@NonNull Call<Recommend> call, @NonNull Response<Recommend> response) {
                List<ItemWrapper> data = model.obtainAlbums(response.body());
                mView.onLoadMoreFinish(true, data);
            }

            @Override
            public void onFailure(@NonNull Call<Recommend> call, @NonNull Throwable t) {
                t.printStackTrace();
                mView.onLoadMoreFinish(false, null);
            }
        }));
    }
}

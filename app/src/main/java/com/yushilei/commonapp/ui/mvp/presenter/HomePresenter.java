package com.yushilei.commonapp.ui.mvp.presenter;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.Discovery;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.net.ApiProxy;
import com.yushilei.commonapp.common.retrofit.callback.AbsCallBack;
import com.yushilei.commonapp.common.retrofit.callback.CommonCallBack;
import com.yushilei.commonapp.common.retrofit.NetProxy;
import com.yushilei.commonapp.ui.mvp.contract.HomeContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class HomePresenter extends BasePresenter<HomeContract.IView> implements HomeContract.Presenter {

    private Call<Recommend> mLoadMoreCall;
    /**
     * 是否正处于加载更多状态
     */
    private boolean isLoadingMore = false;

    public HomePresenter(HomeContract.IView view, HomeContract.IModel model) {
        super(view);
        this.model = model;
    }

    private HomeContract.IModel model;
    /**
     * 网络层代理实现
     */
    private NetProxy mNetProxy = new NetProxy() {
        @Override
        public int getTaskId() {
            return mTaskId;
        }
    };

    @Override
    public void beginRefreshData(final boolean isRefreshByUser) {
        if (isRefreshByUser) {
            //用户触发刷新
            mView.onRefreshing();
        } else {
            //App加载触发刷新
            mView.showLoading();
        }
        if (isLoadingMore) {
            //当前正处于加载更多时，进行刷新应该取消view 加载更多状态，并cancel Call
            mLoadMoreCall.cancel();
            mView.onCancelLoadMore();
        }

        //Call<Discovery> call = mNetProxy.getDiscovery();
        Call<Discovery> call = ApiProxy.get(mTaskId).getDiscovery();
        call.enqueue(new CommonCallBack<Discovery>(new AbsCallBack<Discovery>() {

            @Override
            public void onResponse(@NonNull Call<Discovery> call, @NonNull Response<Discovery> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ItemWrapper> items = model.obtainItems(response.body());
                    mView.onRefreshFinish(isRefreshByUser, true, items);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Discovery> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (mView != null)
                    mView.onRefreshFinish(isRefreshByUser, false, null);
            }
        }));
    }

    @Override
    public void beginLoadMore() {
        isLoadingMore = true;
        mLoadMoreCall = mNetProxy.getRecommend(model.getPageId(), model.getPageSize());

        mLoadMoreCall.enqueue(new CommonCallBack<Recommend>(new AbsCallBack<Recommend>() {
            @Override
            public void onResponse(@NonNull Call<Recommend> call, @NonNull Response<Recommend> response) {
                List<ItemWrapper> data = model.obtainAlbums(response.body());
                mView.onLoadMoreFinish(true, data);
                isLoadingMore = false;
            }

            @Override
            public void onFailure(@NonNull Call<Recommend> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (mView != null)
                    mView.onLoadMoreFinish(false, null);
                isLoadingMore = false;
            }
        }));
    }
}

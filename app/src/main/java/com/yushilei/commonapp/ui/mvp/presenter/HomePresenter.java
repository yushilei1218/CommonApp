package com.yushilei.commonapp.ui.mvp.presenter;

import android.support.annotation.NonNull;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.DiscoveryBean;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.net.ApiProxy;

import com.yushilei.commonapp.common.net.N.APIProxy1;
import com.yushilei.commonapp.common.net.NetApi;
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
     * 网络层代理实现(普通代理)
     */
    private NetProxy mNetProxy = new NetProxy(mTaskId);
    /**
     * 编译时注解自动生成的(普通代理)
     */
    private APIProxy1 mCompileProxy = new APIProxy1(NetApi.api, mTaskId);
    /**
     * 利用动态接口实现+反射生成的（动态代理）
     */
    private NetApi.API mDynamicProxy = ApiProxy.get(mTaskId);


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

        /*Call<DiscoveryBean> call = mNetProxy.getDiscovery();*/
        /*Call<DiscoveryBean> call = mDynamicProxy.getDiscovery();*/

        Call<DiscoveryBean> call = mCompileProxy.getDiscovery();

        call.enqueue(new CommonCallBack<DiscoveryBean>() {
            @Override
            public void onBizResponse(@NonNull Call<DiscoveryBean> call, @NonNull Response<DiscoveryBean> response) {
                /*P层利用Model整合数据*/
                List<ItemWrapper> items = model.obtainItems(response.body());
                /*P层把数据推送给V层*/
                mView.onRefreshFinish(isRefreshByUser, true, items);
            }

            @Override
            public void onBizFailure(@NonNull Call<DiscoveryBean> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (mView != null)
                    mView.onRefreshFinish(isRefreshByUser, false, null);
            }

            @Override
            protected boolean onTimeOutException(@NonNull Call<DiscoveryBean> call, @NonNull Throwable t) {
                return true;
            }
        });
    }

    @Override
    public void beginLoadMore() {
        isLoadingMore = true;
        mLoadMoreCall = mNetProxy.getRecommend(model.getPageId(), model.getPageSize());
        mLoadMoreCall.enqueue(new CommonCallBack<Recommend>() {
            @Override
            public void onBizResponse(@NonNull Call<Recommend> call, @NonNull Response<Recommend> response) {
                List<ItemWrapper> data = model.obtainAlbums(response.body());
                mView.onLoadMoreFinish(true, data);
                isLoadingMore = false;
            }

            @Override
            public void onBizFailure(@NonNull Call<Recommend> call, @NonNull Throwable t) {
                if (mView != null)
                    mView.onLoadMoreFinish(false, null);
                isLoadingMore = false;
            }
        });
    }
}

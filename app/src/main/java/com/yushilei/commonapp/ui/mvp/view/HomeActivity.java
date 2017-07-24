package com.yushilei.commonapp.ui.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.common.widget.LoadMoreRecyclerView;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;
import com.yushilei.commonapp.ui.mvp.contract.HomeContract;
import com.yushilei.commonapp.ui.mvp.model.HomeModel;
import com.yushilei.commonapp.ui.mvp.presenter.HomePresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class HomeActivity extends MvpBaseActivity<HomeContract.Presenter> implements HomeContract.IView, LoadMoreRecyclerView.LoadMoreListener {

    private PtrFrameLayout mPtr;
    private LoadMoreRecyclerView mRecycler;
    private MultiRecyclerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeContract.Presenter getPresenter() {
        return new HomePresenter(this, new HomeModel());
    }

    @Override
    public void initView() {
        mPtr = findView(R.id.home_ptr);
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.setHeaderView(header);
        mPtr.addPtrUIHandler(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.beginRefreshData(true);
            }
        });
        mRecycler = findView(R.id.home_recycler);
        adapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(adapter);
        mRecycler.setLoadMoreListener(this);

        presenter.beginRefreshData(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefreshing() {
        mRecycler.setCanLoadMore(false);
    }

    @Override
    public void onRefreshFinish(boolean isByRefresh, boolean isNetSuccess, List<ItemWrapper> data) {
        mRecycler.setCanLoadMore(true);
        if (!isByRefresh) {
            hideLoading();
        }
        if (isNetSuccess) {
            showToast("网络异常！");
            if (!isByRefresh) {
                onError(0, "网络异常,点击重试!", "点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.beginRefreshData(false);
                    }
                });
            }
            return;
        }
        adapter.addAll(data);
        mRecycler.loadFinish();
    }

    /*LoadMoreRecyclerView 触发加载更多回调*/
    @Override
    public void onLoadMore() {
        presenter.beginLoadMore();
    }
}

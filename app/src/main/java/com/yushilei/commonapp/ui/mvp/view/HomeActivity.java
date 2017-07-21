package com.yushilei.commonapp.ui.mvp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;
import com.yushilei.commonapp.ui.mvp.contract.HomeContract;
import com.yushilei.commonapp.ui.mvp.presenter.HomePresenter;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class HomeActivity extends MvpBaseActivity<HomeContract.Presenter> implements HomeContract.IView {

    private PtrFrameLayout mPtr;
    private RecyclerView mRecycler;
    private MultiRecyclerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeContract.Presenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void initView() {
        mPtr = (PtrFrameLayout) findView(R.id.home_ptr);
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.setHeaderView(header);
        mPtr.addPtrUIHandler(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refreshData();
            }
        });
        mRecycler = (RecyclerView) findView(R.id.home_recycler);
        adapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }
}

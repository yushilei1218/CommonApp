package com.yushilei.commonapp.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yushilei.commonapp.common.mvp.OperateViewHolder;
import com.yushilei.commonapp.common.mvp.IBaseView;

/**
 * Fragment 基类
 * <p>
 * 实现{@link IBaseView} 已完成页面基础操作的相关实现；
 * APP内fragment需要继承该Fragment 进行
 */

public abstract class BaseFragment extends Fragment implements IBaseView {

    private SparseArray<View> mViews = new SparseArray<>();

    private OperateViewHolder mHolder;

    private boolean isViewCreated = false;
    private boolean isVisibleToUser = false;
    private boolean isLoaded = false;

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {

            parseDataByArguments(getArguments());
        } else {
            restoreDataBySavedBundle(savedInstanceState);
        }
        super.onCreate(savedInstanceState);
    }

    protected void lazyLoad() {
    }

    protected void parseDataByArguments(Bundle arguments) {
    }

    protected void restoreDataBySavedBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState == null) {
            outState = new Bundle();
        }
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
        initOperateView();
        initView();
        initData();
        checkToLazyLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkToLazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        checkToLazyLoad();
        checkViewState(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        checkToLazyLoad();
        checkViewState(!hidden);
    }

    private void checkViewState(boolean isVisible) {
        if (isViewCreated) {
            if (isVisible) {
                onActiveView();
            } else {
                onSleepView();
            }
        }
    }

    protected void onSleepView() {

    }

    protected void onActiveView() {

    }

    private void checkToLazyLoad() {
        if (isLoaded)
            return;
        if (isViewCreated && getUserVisibleHint()) {
            isLoaded = true;
            lazyLoad();
        }
    }

    @Override
    public void onDestroyView() {
        isViewCreated = false;
        isLoaded = false;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mHolder = null;
        mViews = null;
        super.onDestroy();
    }

    /****点击事件绑定部分****/
    protected void setOnClick(View v) {
        v.setOnClickListener(this);
    }

    protected void setOnClick(View... views) {
        for (View v : views) {
            setOnClick(v);
        }
    }

    protected void setOnClick(@IdRes int rid) {
        findView(rid).setOnClickListener(this);
    }

    protected void setOnClick(@IdRes int... rids) {
        for (int rid : rids) {
            setOnClick(rid);
        }
    }

    /****IBaseView 实现部分****/

    @Override
    public Activity getActivityContext() {
        return getActivity();
    }

    @Override
    public void initData() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(BaseApp.AppContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(AlertDialog dialog) {
        dialog.show();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public String getTAG() {
        String name = this.getClass().getSimpleName();
        if (name.length() > 23)
            name = name.substring(0, 23);
        return name;
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public <T extends View> T findView(int rid) {
        View view = mViews.get(rid);
        if (view == null) {
            view = getView().findViewById(rid);
            mViews.append(rid, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initOperateView() {
        mHolder = new OperateViewHolder(this);
    }

    @Override
    public void showLoading() {
        mHolder.showLoading();
    }

    @Override
    public void hideLoading() {
        mHolder.hideLoading();
    }

    @Override
    public void showLoadingDialog() {
        mHolder.showLoadingDialog();
    }

    @Override
    public void hideLoadingDialog() {
        mHolder.hideLoadingDialog();
    }

    @Override
    public void onError(int imageId, String msg, String btnText, View.OnClickListener onClickListener) {
        mHolder.onError(imageId, msg, btnText, onClickListener);
    }
}

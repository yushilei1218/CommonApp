package com.yushilei.commonapp.common.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.trello.navi2.Event;
import com.trello.navi2.Listener;
import com.trello.navi2.NaviComponent;
import com.trello.navi2.component.support.NaviAppCompatActivity;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.navi.NaviLifecycle;
import com.umeng.analytics.MobclickAgent;
import com.yushilei.commonapp.common.manager.ActivityStack;
import com.yushilei.commonapp.common.mvp.OperateViewHolder;
import com.yushilei.commonapp.common.mvp.IBaseView;

/**
 * Activity基类
 */

public abstract class BaseActivity extends NaviAppCompatActivity implements IBaseView {
    /**
     * 当前Activity是否处于活动状态
     */
    private boolean isResume = false;
    /**
     * 用于存储 或查询 当前Activity findViewById() 查找过的View
     */
    private SparseArray<View> mViews = new SparseArray<>();

    private OperateViewHolder mHolder;

    protected LifecycleProvider<ActivityEvent> mProvider;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            restoreDataBySavedBundle(savedInstanceState);
        } else {
            parseDataByIntent(getIntent());
        }
        super.onCreate(savedInstanceState);
        ActivityStack.inStack(this);

        mProvider = NaviLifecycle.createActivityLifecycleProvider(this);

        setContentView(getLayoutId());

        initOperateView();

        initView();

        initData();
    }

    /**
     * 数据恢复
     *
     * @param savedInstanceState {@link #onCreate(Bundle)}
     */
    protected void restoreDataBySavedBundle(Bundle savedInstanceState) {

    }

    /**
     * 解析Intent数据
     *
     * @param intent {@link #getIntent()}
     */
    protected void parseDataByIntent(Intent intent) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int rid) {
        View view = mViews.get(rid);
        if (view == null) {
            view = findViewById(rid);
            mViews.append(rid, view);
        }
        return (T) view;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public boolean isResume() {
        return isResume;
    }

    @Override
    public void initData() {

    }

    //***点击事件绑定开始
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

    @Override
    public void onClick(View v) {

    }

    //***点击事件绑定结束
    @Override
    public String getTAG() {
        String name = this.getClass().getSimpleName();
        if (name.length() > 23) {
            name = name.substring(0, 23);
        }
        return name;
    }

    @Override
    public Activity getActivityContext() {
        return this;
    }

    @Override
    public void showDialog(AlertDialog dialog) {
        dialog.show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(BaseApp.AppContext, msg, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isResume = false;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        isResume = true;
    }

    @Override
    protected void onPause() {
        isResume = false;
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        ActivityStack.outStack(this);
        super.onDestroy();
    }
}

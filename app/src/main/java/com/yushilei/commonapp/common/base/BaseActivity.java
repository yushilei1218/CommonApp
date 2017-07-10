package com.yushilei.commonapp.common.base;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.yushilei.commonapp.common.manager.ActivityStack;

/**
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 当前Activity是否处于活动状态
     */
    private boolean isResume = false;
    /**
     * 用于存储 或查询 当前Activity findViewById() 查找过的View
     */
    private SparseArray<View> mViews = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.inStack(this);
        setContentView(getLayoutId());
        initView();
        initData();
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int rid) {
        View view = mViews.get(rid);
        if (view == null) {
            view = findViewById(rid);
            mViews.append(rid, view);
        }
        return (T) view;
    }

    protected abstract void initView();

    protected void initData() {
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public void showDialog(AlertDialog dialog) {
        dialog.show();
    }

    public void showToast(String msg) {
        Toast.makeText(BaseApp.AppContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isResume = false;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onPause() {
        isResume = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ActivityStack.outStack(this);
        super.onDestroy();
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

}

package com.yushilei.commonapp.common.base;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.yushilei.commonapp.common.manager.ActivityStack;

/**
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
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

    @LayoutRes
    protected abstract int getLayoutId();

    public void showDialog(AlertDialog dialog) {
        dialog.show();
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
}

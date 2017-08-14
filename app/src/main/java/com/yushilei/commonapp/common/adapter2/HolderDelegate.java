package com.yushilei.commonapp.common.adapter2;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @auther by yushilei.
 * @time 2017/8/13-14:14
 * @desc
 */

public abstract class HolderDelegate<BEAN> {

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void onBindData(BaseRecyclerHolder holder, BEAN bean, int pos);


    public int getViewType() {
        return getLayoutId();
    }

    public void onItemClick(View target, BaseRecyclerHolder<BEAN> holder, BEAN bean) {
    }

    public boolean onItemLongClick(View target, BaseRecyclerHolder<BEAN> holder, BEAN bean) {
        return false;
    }

    public boolean onTouch(View v, MotionEvent event, BaseRecyclerHolder<BEAN> holder, BEAN bean) {
        return false;
    }
}

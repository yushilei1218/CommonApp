package com.yushilei.commonapp.common.adapter2;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * @auther by yushilei.
 * @time 2017/8/13-17:06
 * @desc
 */

public class BaseRecyclerHolder<Bean> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
    private final HolderDelegate<Bean> mDelegate;
    public Bean bean;

    private SparseArray<View> mViews = new SparseArray<>();

    public BaseRecyclerHolder(View itemView, HolderDelegate<Bean> delegate) {
        super(itemView);
        this.mDelegate = delegate;
    }

    public int getLayoutId() {
        return mDelegate.getLayoutId();
    }

    final void onKeepBindData(Bean bean, int pos) {
        this.bean = bean;
        mDelegate.onBindData(this, bean, pos);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int rid) {
        View view = mViews.get(rid);
        if (view == null) {
            view = itemView.findViewById(rid);
            mViews.append(rid, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        mDelegate.onItemClick(v, this, bean);
    }

    @Override
    public boolean onLongClick(View v) {
        return mDelegate.onItemLongClick(v, this, bean);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mDelegate.onTouch(v, event, this, bean);
    }
}

package com.yushilei.commonapp.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by shilei.yu on 2017/7/9.
 */

public abstract class BaseViewHolder<Bean> extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(BaseViewHolder holder, Bean bean, int pos);

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int rid) {
        View view = mViews.get(rid);
        if (view == null) {
            view = itemView.findViewById(rid);
            mViews.append(rid, view);
        }
        return (T) view;
    }
}

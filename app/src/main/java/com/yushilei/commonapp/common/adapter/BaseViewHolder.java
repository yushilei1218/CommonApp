package com.yushilei.commonapp.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder基类封装
 * {@link MultiBaseAdapter}适配 AdapterView
 * {@link MultiRecyclerAdapter} 适配RecyclerView
 *
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
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
}

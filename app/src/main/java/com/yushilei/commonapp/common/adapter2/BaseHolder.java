package com.yushilei.commonapp.common.adapter2;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @auther by yushilei.
 * @time 2017/8/13-14:14
 * @desc
 */

public abstract class BaseHolder<BEAN> {
    private SparseArray<View> mViews = new SparseArray<>();

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void onBindData(View itemView, BEAN bean, int pos);

    public int getViewType() {
        return getLayoutId();
    }

//    @SuppressWarnings("unchecked")
//    public <T extends View> T findView(int rid) {
//        View view = mViews.get(rid);
//        if (view == null) {
//            view = itemView.findViewById(rid);
//            mViews.append(rid, view);
//        }
//        return (T) view;
//    }
}

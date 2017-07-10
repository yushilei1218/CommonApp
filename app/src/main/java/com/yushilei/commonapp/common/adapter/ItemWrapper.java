package com.yushilei.commonapp.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView or AdapterView 数据item包装类
 * 适配{@link MultiBaseAdapter} ListView or GridView
 * 适配{@link MultiRecyclerAdapter} RecyclerView
 *
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public abstract class ItemWrapper<T> implements Item {
    public T bean;

    public ItemWrapper(T bean) {
        this.bean = bean;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
        加载当前数据项指定的布局id 作为converterView 即 holder中的itemView
         */
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false);
        BaseViewHolder holder = new BaseViewHolder(itemView);
        itemView.setTag(holder);
        return holder;
    }

    @Override
    public int getItemViewType() {
        return getLayoutRes();
    }
}

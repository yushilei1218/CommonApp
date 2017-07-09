package com.yushilei.commonapp.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shilei.yu on 2017/7/9.
 */

public abstract class BaseItem<T> implements Item {
    public T bean;

    public BaseItem(T bean) {
        this.bean = bean;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false);
        return new BaseViewHolder<T>(itemView) {

            @Override
            public void onBindViewHolder(BaseViewHolder holder, T t, int pos) {
                BaseItem.this.onBindViewHolder(holder, t, pos);
            }
        };
    }

    @Override
    public int getItemViewType() {
        return getLayoutRes();
    }

    public abstract void onBindViewHolder(BaseViewHolder holder, T t, int pos);
}

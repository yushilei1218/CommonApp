package com.yushilei.commonapp.common.adapter2;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @auther by yushilei.
 * @time 2017/8/13-17:06
 * @desc
 */

public class BaseRecyclerHolder<Bean> extends RecyclerView.ViewHolder {
    private final BaseHolder<Bean> holder;
    private Bean bean;

    public BaseRecyclerHolder(View itemView, BaseHolder<Bean> holder) {
        super(itemView);
        this.holder = holder;
    }

    public int getLayoutId() {
        return holder.getLayoutId();
    }

    final void onBindData(Bean bean, int pos) {
        this.bean = bean;
        holder.onBindData(itemView, bean, pos);
    }

}

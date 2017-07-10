package com.yushilei.commonapp.common.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * 列表数据适配Adapter 接口
 *
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public interface Item<ViewHolder extends BaseViewHolder> {
    /**
     * 获取当前Item 布局类型
     * 在{@link ItemWrapper}中，默认的实现为 return {@link ItemWrapper#getLayoutRes()} ()}
     *
     * @return int
     */
    int getItemViewType();

    /**
     * 为当前的数据项创建ViewHolder
     */
    ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 获取当前数据项 需要展示的布局
     */
    @LayoutRes
    int getLayoutRes();

    /**
     * 数据绑定展示
     *
     * @param holder 经itemView绑定的BaseViewHolder
     * @param pos    当前绑定的数据项在数据源的位置
     */
    void onBindViewHolder(BaseViewHolder holder, int pos);
}

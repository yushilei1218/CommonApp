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
    int getItemViewType();

    ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @LayoutRes
    int getLayoutRes();

    void onBindViewHolder(BaseViewHolder holder, int pos);
}

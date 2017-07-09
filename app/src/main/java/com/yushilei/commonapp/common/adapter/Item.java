package com.yushilei.commonapp.common.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shilei.yu on 2017/7/9.
 */

public interface Item<ViewHolder extends BaseViewHolder> {
    int getItemViewType();

    ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @LayoutRes
    int getLayoutRes();
}

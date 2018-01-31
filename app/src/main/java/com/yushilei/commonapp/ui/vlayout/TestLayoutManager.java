package com.yushilei.commonapp.ui.vlayout;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author shilei.yu
 * @since on 2018/1/22.
 */

public class TestLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

    }
}

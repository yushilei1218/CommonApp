package com.yushilei.commonapp.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.yushilei.commonapp.common.util.SetUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 试用于RecyclerView
 * 多布局复用统一封装Adapter
 *
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public class MultiRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<ItemWrapper> mData = new LinkedList<>();

    public MultiRecyclerAdapter() {
    }

    public void addAll(List<ItemWrapper> data) {
        if (data != null && data.size() > 0) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        } else {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (mData != null)
            mData.clear();
        notifyDataSetChanged();
    }

    public void remove(ItemWrapper item) {
        if (!SetUtil.isEmpty(mData)) {
            int index = mData.indexOf(item);
            if (index > -1) {
                mData.remove(index);
                notifyItemRemoved(index);
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mData != null && mData.size() > 0) {
            for (ItemWrapper item : mData) {
                if (item.getItemViewType() == viewType) {
                    return item.onCreateViewHolder(parent, viewType);
                }
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ItemWrapper item = mData.get(position);
        item.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemViewType();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}

package com.yushilei.commonapp.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shilei.yu on 2017/7/9.
 */

public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<BaseItem> mData = new LinkedList<>();
    Context context;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<BaseItem> data) {
        if (data != null && data.size() > 0) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        } else {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mData != null && mData.size() > 0) {
            for (BaseItem item : mData) {
                if (item.getItemViewType() == viewType) {
                    return item.onCreateViewHolder(parent, viewType);
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        BaseItem item = mData.get(position);
        holder.onBindViewHolder(holder, item.bean, position);
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

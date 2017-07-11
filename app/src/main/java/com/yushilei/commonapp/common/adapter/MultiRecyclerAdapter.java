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

    /**
     * 清空原数据集合，将新的数据集合添加到数据源中并刷新
     */
    public void addAll(List<ItemWrapper> data) {
        if (!SetUtil.isEmpty(data)) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        } else {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void addAllLast(List<ItemWrapper> data) {
        if (SetUtil.isEmpty(data))
            return;
        if (mData == null)
            mData = new LinkedList<>();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addLast(ItemWrapper last) {
        if (mData == null) {
            mData = new LinkedList<>();
        }
        mData.add(last);
        notifyItemInserted(mData.indexOf(last));

    }

    public int indexOf(ItemWrapper item) {
        if (SetUtil.isEmpty(mData))
            return -1;
        return mData.indexOf(item);
    }

    /**
     * 清空数据源并刷新RecyclerView
     */
    public void clear() {
        if (mData != null)
            mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 移除数据源中的某一项，并刷新
     */
    public void remove(ItemWrapper item) {
        if (!SetUtil.isEmpty(mData)) {
            int index = mData.indexOf(item);
            if (index > -1) {
                mData.remove(index);
                notifyItemRemoved(index);
            }
        }
    }

    /**
     * 刷新数据源中的某一项
     */
    public void update(ItemWrapper item) {
        if (!SetUtil.isEmpty(mData)) {
            int index = mData.indexOf(item);
            if (index > -1) {
                notifyItemChanged(index);
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
        根据当前的viewType 从数据源中ItemWrapper 找到匹配的数据项，并由数据项创建匹配的BaseViewHolder
         */
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

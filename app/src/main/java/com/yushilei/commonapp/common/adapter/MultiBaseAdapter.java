package com.yushilei.commonapp.common.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yushilei.commonapp.common.util.SetUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 试用于ListView 或者GridView
 * 多布局复用统一封装Adapter
 *
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public class MultiBaseAdapter extends BaseAdapter {
    /**
     * 数据源
     */
    private List<ItemWrapper> mData = new LinkedList<>();
    /**
     * 用于设定当前AdapterView 支持的布局类型总数
     */
    private int mViewTypeCount;
    /**
     * 由于BaseAdapter {@see getItemViewType}必须从0开始
     * 故用该mViewTypeArr 一一对应 ItemWrapper中的getViewType();
     */
    @SuppressWarnings("unchecked")
    private SparseArray<Integer> mViewTypeArr = new SparseArray();

    public MultiBaseAdapter(int viewTypeCount) {
        this.mViewTypeCount = viewTypeCount;
    }

    public void addAll(List<ItemWrapper> data) {
        if (data != null && data.size() > 0) {
            mData.clear();
            mData.addAll(data);
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    public void remove(ItemWrapper item) {
        if (!SetUtil.isEmpty(mData)) {
            if (mData.contains(item)) {
                mData.remove(item);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * ViewType 必须从0 开始
     */
    @Override
    public int getItemViewType(int position) {
        //形成 ItemWrapper.getItemViewType() 与ViewType一一对应关系
        int viewType = 0;
        if (mData != null && mData.size() > 0) {
            for (ItemWrapper i : mData) {
                Integer integer = mViewTypeArr.get(i.getItemViewType());
                if (integer == null) {
                    mViewTypeArr.append(i.getItemViewType(), viewType);
                    viewType++;
                }
            }
            return mViewTypeArr.get(mData.get(position).getItemViewType());
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeCount;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        ItemWrapper item = mData.get(position);

        if (convertView == null) {
            BaseViewHolder holder = item.onCreateViewHolder(parent, itemViewType);
            convertView = holder.itemView;
        }
        BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
        item.onBindViewHolder(holder, position);
        return convertView;
    }
}

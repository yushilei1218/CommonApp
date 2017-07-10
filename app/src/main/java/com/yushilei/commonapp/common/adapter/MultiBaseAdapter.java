package com.yushilei.commonapp.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
    private List<ItemWrapper> mData = new LinkedList<>();

    public MultiBaseAdapter() {
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

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemViewType();
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemWrapper item = mData.get(position);

        if (convertView == null) {
            BaseViewHolder holder = item.onCreateViewHolder(parent, 0);
            convertView = holder.itemView;
        }
        BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
        item.onBindViewHolder(holder, position);
        return convertView;
    }
}

package com.yushilei.commonapp.common.adapter2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther by yushilei.
 * @time 2017/8/13-17:10
 * @desc
 */

public class MultiListAdapter extends BaseAdapter {
    private List data = new ArrayList();
    private int typeCount;

    public MultiListAdapter(int typeCount) {
        this.typeCount = typeCount;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return typeCount;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

package com.yushilei.commonapp.common.adapter2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yushilei.commonapp.common.util.SetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther by yushilei.
 * @time 2017/8/13-17:10
 * @desc
 */

@SuppressWarnings("unchecked")
public class MultiListAdapter extends BaseAdapter {
    private List data = new ArrayList();
    private int typeCount;
    private Map<Class, HolderDelegate> beanDelegateMap = new HashMap<>();
    private Map<HolderDelegate, Integer> delegateTypeMap = new HashMap<>();

    public MultiListAdapter(int typeCount) {
        this.typeCount = typeCount;
    }

    public void setMatch(Class beanClass, HolderDelegate delegate) {
        beanDelegateMap.put(beanClass, delegate);
        delegateTypeMap.put(delegate, beanDelegateMap.size() - 1);
    }

    public void replaceData(List newData) {
        if (data == null)
            data = new ArrayList();
        data.clear();
        if (!SetUtil.isEmpty(newData))
            data.addAll(newData);
        notifyDataSetChanged();
    }

    public void addAll(List newData) {
        if (data == null)
            data = new ArrayList();

        if (!SetUtil.isEmpty(newData))
            data.addAll(newData);
        notifyDataSetChanged();
    }

    public boolean remove(Object bean) {
        int pos = data.indexOf(bean);
        if (pos < 0)
            return false;
        data.remove(pos);
        notifyDataSetChanged();
        return true;
    }

    public boolean update(Object bean) {
        int pos = data.indexOf(bean);
        if (pos < 0)
            return false;
        notifyDataSetChanged();
        return true;
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

    /*type 从0开始*/
    @Override
    public int getItemViewType(int position) {
        Object bean = data.get(position);
        HolderDelegate delegate = beanDelegateMap.get(bean.getClass());
        return delegateTypeMap.get(delegate);
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object bean = data.get(position);
        HolderDelegate delegate = beanDelegateMap.get(bean.getClass());
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(delegate.getLayoutId(), parent, false);
            convertView.setTag(new BaseRecyclerHolder(convertView, delegate));
        }
        BaseRecyclerHolder holder = (BaseRecyclerHolder) convertView.getTag();
        holder.onKeepBindData(bean, position);

        return convertView;
    }
}

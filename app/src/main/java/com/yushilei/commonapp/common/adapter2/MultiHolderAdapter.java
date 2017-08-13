package com.yushilei.commonapp.common.adapter2;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.commonapp.common.util.SetUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther by yushilei.
 * @time 2017/8/13-14:14
 * @desc
 */
@SuppressWarnings("unchecked")
public class MultiHolderAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List data = new ArrayList();

    private Map<Class, BaseHolder> map = new HashMap<>();
    private SparseArray<BaseHolder> map2 = new SparseArray<>();

    public void addAll(List newData) {
        if (data == null) {
            data = new ArrayList();
        }
        if (!SetUtil.isEmpty(newData)) {
            data.addAll(newData);
            notifyItemRangeInserted(data.size(), data.size() + newData.size());
        }
    }

    public void replaceData(List newData) {
        data.clear();
        if (!SetUtil.isEmpty(newData)) {
            data.addAll(newData);
        }
        notifyDataSetChanged();
    }

    public boolean remove(Object bean) {
        int pos = data.indexOf(bean);
        if (pos < 0)
            return false;
        data.remove(pos);
        notifyItemRemoved(pos);
        return true;
    }

    public void setMatch(Class beanClass, BaseHolder holder) {
        map.put(beanClass, holder);
        map2.put(holder.getViewType(), holder);
    }

    @Override
    public int getItemViewType(int position) {
        BaseHolder holder = map.get(data.get(position).getClass());
        return holder.getViewType();
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = map2.get(viewType);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(holder.getLayoutId(), parent, false);
        return new BaseRecyclerHolder(itemView, holder);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        Object bean = data.get(position);
        holder.onBindData(bean, position);
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

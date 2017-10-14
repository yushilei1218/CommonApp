package com.yushilei.commonapp.common.loadmore;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.util.SetUtil;

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
    private List data;
    private LoadRecyclerView.FootItem mFooter;

    private Map<Class, HolderDelegate> beanDelegateMap = new HashMap<>();
    private SparseArray<HolderDelegate> typeDelegateMap = new SparseArray<>();

    public void setRoot(List root) {
        data = root;
        notifyDataSetChanged();
    }

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
        if (data == null) {
            data = new ArrayList();
        }
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

    public boolean update(Object bean) {
        int pos = data.indexOf(bean);
        if (pos < 0)
            return false;
        notifyItemChanged(pos);
        return true;
    }

    public void addFooter(LoadRecyclerView.FootItem footItem) {
        if (mFooter == null) {
            mFooter = footItem;
            notifyItemInserted(getItemCount() - 1);
        } else {
            mFooter = footItem;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void removeFooter() {
        if (mFooter != null) {
            mFooter = null;
            notifyItemRemoved(getItemCount());
        }
    }

    public int indexOf(Object item) {
        return data.indexOf(item);
    }

    public void setMatch(Class beanClass, HolderDelegate delegate) {
        beanDelegateMap.put(beanClass, delegate);
        typeDelegateMap.put(delegate.getViewType(), delegate);
    }

    @Override
    public int getItemViewType(int position) {
        HolderDelegate delegate;
        if (SetUtil.isEmpty(data) || data.size() == position) {
            delegate = beanDelegateMap.get(mFooter.getClass());
            return delegate.getViewType();
        }
        delegate = beanDelegateMap.get(data.get(position).getClass());
        return delegate.getViewType();
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HolderDelegate delegate = typeDelegateMap.get(viewType);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(delegate.getLayoutId(), parent, false);
        return new BaseRecyclerHolder(itemView, delegate);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (SetUtil.isEmpty(data) || data.size() == position) {
            holder.onKeepBindData(mFooter, position);
        } else {
            Object bean = data.get(position);
            holder.onKeepBindData(bean, position);
        }
    }


    @Override
    public int getItemCount() {
        int dataSize = data == null ? 0 : data.size();
        if (mFooter != null) {
            dataSize++;
        }
        return dataSize;
    }
}

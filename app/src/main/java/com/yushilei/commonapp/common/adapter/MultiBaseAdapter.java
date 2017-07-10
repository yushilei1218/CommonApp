package com.yushilei.commonapp.common.adapter;

import android.util.Log;
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
 * @see #getItemViewType(int) BaseAdapter的该方法返回值必须从0开始
 * mViewTypeArr 用来实现{@link ItemWrapper} getItemViewType() 方法与 BaseAdapter getItemViewType()的对应关系
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
     * ViewTypeArr value 从0开始根据mViewTypeCount的数量递增
     * ViewTypeArr 一一对应 ItemWrapper中的getViewType();
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

    public void clear() {
        if (!SetUtil.isEmpty(mData)) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void update(ItemWrapper item) {
        if (!SetUtil.isEmpty(mData)) {
            if (mData.contains(item)) {
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
        /*
         *  BaseAdapter会根据当前的ViewType返回convertView
         *  如果convertView==null ，当前数据项ItemWrapper 也会创建正确的convertView并绑定holder
         */
        int itemViewType = getItemViewType(position);
        ItemWrapper item = mData.get(position);

        if (convertView == null) {
            /*
             * 创建BaseViewHolder的时候 内部已经将该Holder与convertView进行绑定
             */
            BaseViewHolder holder = item.onCreateViewHolder(parent, itemViewType);
            convertView = holder.itemView;
        }
        BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
        /*
         *直接调用数据项的 onBindViewHolder 方法实现Holder convertView 数据项三者的绑定
         */
        item.onBindViewHolder(holder, position);
        return convertView;
    }
}

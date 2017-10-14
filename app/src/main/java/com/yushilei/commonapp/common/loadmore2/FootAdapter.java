package com.yushilei.commonapp.common.loadmore2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;


/**
 * @author shilei.yu
 * @since on 2017/10/14.
 */

public class FootAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {
    private MultiHolderAdapter mAdapter;
    private FootItem mFooter;
    private FootDelegate mFootDelegate = new FootDelegate();

    public FootAdapter(MultiHolderAdapter adapter) {
        mAdapter = adapter;
    }

    public void addFoot(FootItem footer) {
        if (mFooter == null) {
            mFooter = footer;
            notifyItemInserted(getItemCount() - 1);
        } else {
            mFooter = footer;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void removeFoot() {
        if (mFooter != null) {
            mFooter = null;
            notifyItemRemoved(getItemCount());
        }
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == mFootDelegate.getViewType()) {
            return mAdapter.createViewBaseHolder(parent, mFootDelegate);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        int itemCount = mAdapter.getItemCount();
        if (itemCount == 0 || position == itemCount) {
            holder.onKeepBindData(mFooter, position);
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = mAdapter.getItemCount();
        if (itemCount == 0 || position == itemCount) {
            return mFootDelegate.getViewType();
        }
        return mAdapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int count = mAdapter.getItemCount();
        if (mFooter != null) {
            count++;
        }
        return count;
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mAdapter.registerAdapterDataObserver(observer);
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mAdapter.unregisterAdapterDataObserver(observer);
        super.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mAdapter.onDetachedFromRecyclerView(recyclerView);
        super.onDetachedFromRecyclerView(recyclerView);
    }
}

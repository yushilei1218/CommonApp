package com.yushilei.commonapp.ui.feizhu.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.ui.feizhu.FeizhuConstract;
import com.yushilei.commonapp.ui.feizhu.bean.SortBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shilei.yu
 * @since on 2018/2/9.
 */

public abstract class BaseFilter implements FeizhuConstract.IFilter {
    @BindView(R.id.filter_sort_lv)
    ListView mSortLv;
    @BindView(R.id.filter_star_layout)
    View mStartLayout;
    @BindView(R.id.filter_location_layout)
    View mLocationLayout;

    private final ViewGroup mRoot;
    private MultiListAdapter mSortAdapter;

    public BaseFilter(ViewGroup filterLayout) {
        mRoot = filterLayout;
        ButterKnife.bind(this, filterLayout);
    }

    @Override
    public void showSortFilterView(List<SortBean> data) {
        mRoot.setVisibility(View.VISIBLE);
        show(mSortLv);
        if (mSortAdapter == null) {
            mSortAdapter = new MultiListAdapter(1);
            mSortAdapter.setMatch(SortBean.class, new SortDelegate());
            mSortLv.setAdapter(mSortAdapter);
        }
        mSortAdapter.replaceData(data);
    }

    @Override
    public void showStarFilterView() {
        mRoot.setVisibility(View.VISIBLE);
        show(mStartLayout);
    }

    @Override
    public void showLocationFilterView() {
        mRoot.setVisibility(View.VISIBLE);
        show(mLocationLayout);
    }

    @Override
    public void hideFilterLayout() {
        mRoot.setVisibility(View.GONE);
    }

    public void show(View target) {
        for (int i = 0; i < mRoot.getChildCount(); i++) {
            View childAt = mRoot.getChildAt(i);
            if (childAt.equals(target)) {
                childAt.setVisibility(View.VISIBLE);
            } else {
                childAt.setVisibility(View.GONE);
            }
        }
    }

    private final class SortDelegate extends HolderDelegate<SortBean> {

        @Override
        public int getLayoutId() {
            return R.layout.item_sort;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, SortBean sortBean, int pos) {
            ((TextView) holder.findView(R.id.sort_tv)).setText(sortBean.name);
            holder.findView(R.id.sort_tag).setVisibility(sortBean.isSelected() ? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<SortBean> holder, SortBean sortBean) {
            mRoot.setVisibility(View.GONE);
            sortBean.setSelected();
            onSortClicked(sortBean);
        }
    }

    public abstract void onSortClicked(SortBean sortBean);
}

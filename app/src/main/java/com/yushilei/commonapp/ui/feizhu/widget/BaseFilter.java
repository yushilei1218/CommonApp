package com.yushilei.commonapp.ui.feizhu.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.widget.SeekBar;
import com.yushilei.commonapp.ui.feizhu.FeizhuConstract;
import com.yushilei.commonapp.ui.feizhu.bean.HotelType;
import com.yushilei.commonapp.ui.feizhu.bean.PriceBean;
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

    @BindView(R.id.filter_type_grid)
    GridView mStarGrid;
    @BindView(R.id.filter_star_clear)
    View mStarClearTv;
    @BindView(R.id.filter_star_confirm)
    View mStarConfirmTv;
    @BindView(R.id.filter_star_seek)
    SeekBar mSeekBar;

    private final ViewGroup mRoot;
    private MultiListAdapter mSortAdapter;
    private MultiListAdapter mStarAdapter;

    public BaseFilter(ViewGroup filterLayout) {
        mRoot = filterLayout;
        ButterKnife.bind(this, filterLayout);
        mSeekBar.setChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onChange(int min, int max) {
                String msg = "Min= " + min + " Max=" + max;
                Toast.makeText(BaseApp.AppContext,msg , Toast.LENGTH_SHORT).show();
            }
        });
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
    public void showStarFilterView(PriceBean bean, List<HotelType> data) {
        mRoot.setVisibility(View.VISIBLE);
        show(mStartLayout);
        if (mStarAdapter == null) {
            mStarAdapter = new MultiListAdapter(1);
            mStarAdapter.setMatch(HotelType.class, new HotelTypeDelegate());
            mStarGrid.setAdapter(mStarAdapter);
        }
        mStarAdapter.replaceData(data);
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

    private final class HotelTypeDelegate extends HolderDelegate<HotelType> {

        @Override
        public int getLayoutId() {
            return R.layout.item_hotel_type;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, HotelType hotelType, int pos) {
            ((TextView) holder.findView(R.id.item_hotel_type_tv)).setText(hotelType.name);
            holder.findView(R.id.item_hotel_type_tag).setVisibility(hotelType.isSelected() ? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<HotelType> holder, HotelType hotelType) {
            hotelType.setSelected();
            mStarAdapter.notifyDataSetChanged();
        }
    }

    public abstract void onSortClicked(SortBean sortBean);
}

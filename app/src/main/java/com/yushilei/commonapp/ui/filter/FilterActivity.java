package com.yushilei.commonapp.ui.filter;


import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.filter.Data;
import com.yushilei.commonapp.common.bean.filter.Station;
import com.yushilei.commonapp.common.bean.filter.SubWay;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.common.util.JsonUtil;
import com.yushilei.commonapp.common.widget.LoadListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends MvpBaseActivity<FilterContract.IPresenter> implements FilterContract.IView {

    private View mFormTv;
    private View mLocationTv;
    private LoadListView mContentLv;
    private ViewGroup mFilterContainer;

    private View mFromLayout;
    private View mLocationLayout;
    private MultiListAdapter mLv1Adapter;
    private MultiListAdapter mLv2Adapter;

    private ListView mSubwayLv;
    private ListView mStationLv;
    private MultiListAdapter mSubwayAdapter;
    private MultiListAdapter mStationAdapter;

    private final List<SubWay> mCacheSubs = new ArrayList<>();
    private final List<Station> mCacheStations = new ArrayList<>();
    private TabLayout mTabs;
    private Data mLocationData;
    private Data mOtherData;

    @Override
    public FilterContract.IPresenter getPresenter() {
        return new FilterPresenter(this, new FilterModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter;
    }

    @Override
    public void initView() {

        mFormTv = findView(R.id.act_filter_from);
        mLocationTv = findView(R.id.act_filter_location);

        mFilterContainer = findView(R.id.act_filter_container);
        mLocationLayout = findView(R.id.location_layout);
        mFromLayout = findView(R.id.form_layout);
        mTabs = findView(R.id.act_filter_from_tab);

        mSubwayLv = findView(R.id.location_lv1);
        mStationLv = findView(R.id.location_lv2);

        mContentLv = findView(R.id.act_filter_lv);
        MultiListAdapter adapter = new MultiListAdapter(1);
        adapter.setMatch(BeanA.class, new BeanDelegate());
        mContentLv.setAdapter(adapter);

        mSubwayAdapter = new MultiListAdapter(1);
        mSubwayAdapter.setMatch(SubWay.class, new SubwayDelegate());
        mSubwayLv.setAdapter(mSubwayAdapter);

        mStationAdapter = new MultiListAdapter(1);
        mStationAdapter.setMatch(Station.class, new StationDelegate());
        mStationLv.setAdapter(mStationAdapter);


        adapter.addAll(get());

        setOnClick(mFormTv, mLocationTv);

        initData2();

        initTabs();
    }

    private void initData2() {
        mLocationData = getData();
        mOtherData = getData();

        List<SubWay> subs = mOtherData.subs;
        SubWay subWay1 = subs.get(subs.size() - 1);
        subWay1.setHighLight(true);

        SubWay subWay = mLocationData.subs.get(0);
        subWay.setSelected(true);
        subWay.setHighLight(true);
        mCacheSubs.add(subWay);
    }

    private void initTabs() {
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showToast(tab.getPosition() + "" + tab.getText());
                switch (tab.getPosition()) {
                    case 0:
                        mSubwayAdapter.replaceData(mLocationData.subs);
                        break;
                    case 1:
                        mSubwayAdapter.replaceData(mOtherData.subs);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayout.Tab tab1 = mTabs.newTab();
        tab1.setText("区域");
        TabLayout.Tab tab2 = mTabs.newTab();
        tab2.setText("地铁");
        mTabs.addTab(tab1);
        mTabs.addTab(tab2);
    }

    private Data getData() {
        InputStream open = null;
        try {
            open = BaseApp.AppContext.getAssets().open("filterText");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Data obj = null;
        if (open != null) {
            obj = JsonUtil.getObj(open, Data.class);
        }
        return obj;
    }

    private View mTarget;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_filter_from:
            case R.id.act_filter_location:
                initFilterLayout(v);
                break;
        }
    }

    @Override
    public void initFilterLayout(View target) {
        if (mTarget == null) {
            mTarget = target;
            //open
            mFilterContainer.setVisibility(View.VISIBLE);
            show(target);

        } else if (mTarget.equals(target)) {
            //close
            mFilterContainer.setVisibility(View.GONE);
            mTarget = null;
        } else {
            //隐藏原来的，显示新的
            mTarget = target;
            show(target);
        }
    }

    private void show(View target) {
        switch (target.getId()) {
            case R.id.act_filter_from:
                mLocationLayout.setVisibility(View.GONE);
                mFromLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.act_filter_location:
                mLocationLayout.setVisibility(View.VISIBLE);
                mFromLayout.setVisibility(View.GONE);
                break;
        }
        mFilterContainer.setVisibility(View.VISIBLE);
    }

    private List<BeanA> get() {
        List<BeanA> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new BeanA("item+" + i));
        }
        return data;
    }

    private final class BeanDelegate extends HolderDelegate<BeanA> {

        @Override
        public int getLayoutId() {
            return R.layout.item_a;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_a_tv);
            ImageView img = (ImageView) holder.findView(R.id.item_a_img);
            tv.setText(beanA.name);
            img.setImageResource(R.mipmap.ic_clock);
        }
    }

    private final class SubwayDelegate extends HolderDelegate<SubWay> {

        @Override
        public int getLayoutId() {
            return R.layout.item_filter;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, SubWay subWay, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_filter_tv);
            tv.setText(subWay.getName());

            if (subWay.isHighLight()) {
                tv.setSelected(true);
                mStationAdapter.setRootData(subWay.getStations());
            } else {
                tv.setSelected(false);
            }
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<SubWay> holder, SubWay subWay) {
            showToast(subWay.getName());
            //选中subway
            if (!subWay.isHighLight()) {
                //移除原有的highlight
                subWay.setHighLight(true);
                for (SubWay s : mCacheSubs) {
                    s.setHighLight(false);
                }
                mCacheSubs.clear();
                mCacheSubs.add(subWay);
                mSubwayAdapter.notifyDataSetChanged();

            }
        }
    }

    private final class StationDelegate extends HolderDelegate<Station> {

        @Override
        public int getLayoutId() {
            return R.layout.item_filter;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, Station station, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_filter_tv);
            tv.setText(station.getName());
            if (station.isHighLight()) {
                tv.setSelected(true);
            } else {
                tv.setSelected(false);
            }
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<Station> holder, Station station) {
            showToast(station.getName());
            //选中station
            if (!station.isHighLight()) {
                station.setHighLight(true);
                for (Station s : mCacheStations) {
                    s.setHighLight(false);
                }
                mCacheStations.clear();
                mCacheStations.add(station);
                mStationAdapter.notifyDataSetChanged();
            }
        }
    }
}

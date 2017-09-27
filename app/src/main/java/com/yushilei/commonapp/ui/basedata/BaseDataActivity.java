package com.yushilei.commonapp.ui.basedata;


import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.async.Observable;
import com.yushilei.commonapp.common.async.Observer;
import com.yushilei.commonapp.common.async.Task;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.basedata.City;
import com.yushilei.commonapp.common.bean.basedata.Country;
import com.yushilei.commonapp.common.bean.basedata.Province;
import com.yushilei.commonapp.common.util.BaseUtil;

import java.util.List;

public class BaseDataActivity extends BaseActivity {

    private MultiHolderAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_data;
    }

    @Override
    public void initView() {
        setOnClick(R.id.act_base_data_format);

        RecyclerView recycler = findView(R.id.act_base_data_recycler);
        mAdapter = new MultiHolderAdapter();
        recycler.setAdapter(mAdapter);

        mAdapter.setMatch(Province.class, new ProvinceDelegate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_base_data_format:
                format();
                break;
        }
    }

    private void format() {
        new Observable<Country>(new Task<Country>() {
            @Override
            public Country task() throws Throwable {
                return BaseUtil.getCountry(getApplicationContext());
            }
        }).subscribe(new Observer<Country>() {
            @Override
            public void onComplete(Country data) {
                List<Province> provinceList = data.getProvinceList();
                mAdapter.replaceData(provinceList);
            }

            @Override
            public void onError(Throwable trx) {

            }
        });
    }

    private final class ProvinceDelegate extends HolderDelegate<Province> {
        @Override
        public int getLayoutId() {
            return R.layout.item_province;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, Province province, int pos) {
            TextView p = (TextView) holder.findView(R.id.item_province_tv);
            GridView grid = (GridView) holder.findView(R.id.item_province_city_grid);
            p.setText(province.getName());

            MultiListAdapter adapter = new MultiListAdapter(1);
            adapter.setMatch(City.class, new CityDelegate());
            grid.setAdapter(adapter);

            adapter.replaceData(province.getCityList());
        }
    }

    private final class CityDelegate extends HolderDelegate<City> {

        @Override
        public int getLayoutId() {
            return R.layout.item_city;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, City city, int pos) {
            TextView view = (TextView) holder.findView(R.id.item_city_tv);
            view.setText(city.getName());
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<City> holder, City city) {
            showToast(city.getName());
        }
    }
}

package com.yushilei.commonapp.ui.map;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;

import java.util.LinkedList;
import java.util.List;

public class MapActivity extends BaseActivity {

    private MapView mMapView;
    private MultiRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取地图控件引用
        mMapView = findView(R.id.map_v);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        RecyclerView recycler = findView(R.id.map_recycler);

        adapter = new MultiRecyclerAdapter();
        recycler.setAdapter(adapter);
        List<ItemWrapper> data = new LinkedList<>();
        data.add(new BeanWrapper(new BeanA("你好")));
        data.add(new BeanWrapper(new BeanA("呵呵")));
        data.add(new BeanWrapper(new BeanA("噗噗")));
        data.add(new BeanWrapper(new BeanA("次次")));
        data.add(new BeanWrapper(new BeanA("乐乐")));
        data.add(new BeanWrapper(new BeanA("哟哟")));
        data.add(new BeanWrapper(new BeanA("渣渣")));
        data.add(new BeanWrapper(new BeanA("讷讷")));
        adapter.addAll(data);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    public class BeanWrapper extends ItemWrapper<BeanA> {
        public BeanWrapper(BeanA bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_a;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView tv = holder.findView(R.id.item_a_tv);
            tv.setText(bean.name);
            ImageView img = holder.findView(R.id.item_a_img);
            img.setImageResource(R.mipmap.ic_launcher);
        }
    }
}

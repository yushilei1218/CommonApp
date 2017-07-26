package com.yushilei.commonapp.ui.home;


import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;


import com.yushilei.commonapp.R;

import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;

import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.HomeBean;
import com.yushilei.commonapp.common.bean.net.Discovery;
import com.yushilei.commonapp.common.constant.Constant;
import com.yushilei.commonapp.common.retrofit.Interceptor.AbsCallBack;
import com.yushilei.commonapp.common.retrofit.Interceptor.CommonCallBack;
import com.yushilei.commonapp.common.retrofit.NetProxy;
import com.yushilei.commonapp.ui.loadmorerecycler.LoadMoreRecyclerActivity;
import com.yushilei.commonapp.ui.map.MapActivity;
import com.yushilei.commonapp.ui.multilv.MultiListViewActivity;
import com.yushilei.commonapp.ui.multirecycler.MultiRecyclerActivity;
import com.yushilei.commonapp.ui.mvp.view.HomeActivity;
import com.yushilei.commonapp.ui.ptr.PtrZpActivity;
import com.yushilei.commonapp.ui.test.TestActivity;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends BaseActivity {


    @Override
    public void initView() {
        GridView mGridV = findView(R.id.main_grid);
        MultiBaseAdapter adapter = new MultiBaseAdapter(1);
        mGridV.setAdapter(adapter);
        List<ItemWrapper> data = new LinkedList<>();
        HomeItem item1 = new HomeItem(new HomeBean(Constant.MULTI_RECYCLER));
        HomeItem item2 = new HomeItem(new HomeBean(Constant.MULTI_LIST_VIEW));
        HomeItem item3 = new HomeItem(new HomeBean(Constant.LOAD_MORE_RECYCLER));
        HomeItem item4 = new HomeItem(new HomeBean(Constant.ZP_PTR));
        HomeItem item5 = new HomeItem(new HomeBean(Constant.TEST));
        HomeItem item6 = new HomeItem(new HomeBean(Constant.AMAP));
        HomeItem item7 = new HomeItem(new HomeBean(Constant.XMLY));
        data.add(item1);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        data.add(item5);
        data.add(item6);
        data.add(item7);
        adapter.addAll(data);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public class HomeItem extends ItemWrapper<HomeBean> implements View.OnClickListener {
        public HomeItem(HomeBean bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_grid;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView tv = holder.findView(R.id.item_grid_tv);
            tv.setText(bean.name);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (bean.name) {
                case Constant.MULTI_RECYCLER:
                    intent = new Intent(MainActivity.this, MultiRecyclerActivity.class);
                    break;
                case Constant.MULTI_LIST_VIEW:
                    intent = new Intent(MainActivity.this, MultiListViewActivity.class);
                    break;
                case Constant.LOAD_MORE_RECYCLER:
                    intent = new Intent(MainActivity.this, LoadMoreRecyclerActivity.class);
                    break;
                case Constant.ZP_PTR:
                    intent = new Intent(MainActivity.this, PtrZpActivity.class);
                    break;
                case Constant.TEST:
                    intent = new Intent(MainActivity.this, TestActivity.class);
                    break;
                case Constant.AMAP:
                    intent = new Intent(MainActivity.this, MapActivity.class);
                    break;
                case Constant.XMLY:
                    intent = new Intent(MainActivity.this, HomeActivity.class);
                    break;
            }
            if (intent != null)
                MainActivity.this.startActivity(intent);
        }
    }
}

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
import com.yushilei.commonapp.common.constant.Constant;
import com.yushilei.commonapp.ui.loadmorerecycler.LoadMoreRecyclerActivity;
import com.yushilei.commonapp.ui.multilv.MultiListViewActivity;
import com.yushilei.commonapp.ui.multirecycler.MultiRecyclerActivity;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends BaseActivity {


    @Override
    protected void initView() {
        GridView mGridV = findView(R.id.main_grid);
        MultiBaseAdapter adapter = new MultiBaseAdapter(1);
        mGridV.setAdapter(adapter);
        List<ItemWrapper> data = new LinkedList<>();
        HomeItem item1 = new HomeItem(new HomeBean(Constant.MULTI_RECYCLER));
        HomeItem item2 = new HomeItem(new HomeBean(Constant.MULTI_LIST_VIEW));
        HomeItem item3 = new HomeItem(new HomeBean(Constant.LOAD_MORE_RECYCLER));
        data.add(item1);
        data.add(item2);
        data.add(item3);
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
            Intent intent;
            switch (bean.name) {
                case Constant.MULTI_RECYCLER:
                    intent = new Intent(MainActivity.this, MultiRecyclerActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                case Constant.MULTI_LIST_VIEW:
                    intent = new Intent(MainActivity.this, MultiListViewActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                case Constant.LOAD_MORE_RECYCLER:
                    intent = new Intent(MainActivity.this, LoadMoreRecyclerActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
            }
        }
    }
}

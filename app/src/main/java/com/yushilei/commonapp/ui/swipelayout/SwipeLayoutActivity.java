package com.yushilei.commonapp.ui.swipelayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.widget.SwipeLayout;

import java.util.LinkedList;
import java.util.List;

public class SwipeLayoutActivity extends BaseActivity {

    private MultiRecyclerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swipe_layout;
    }

    @Override
    public void initView() {
        RecyclerView recycler = (RecyclerView) findView(R.id.swipe_recycler);
        adapter = new MultiRecyclerAdapter();
        recycler.setAdapter(adapter);

        adapter.addAll(getData());

    }

    private List<ItemWrapper> getData() {
        List<ItemWrapper> data = new LinkedList<>();
        data.add(new BeanWrapper(new BeanA("1")));
        data.add(new BeanWrapper(new BeanA("2")));
        data.add(new BeanWrapper(new BeanA("3")));
        data.add(new BeanWrapper(new BeanA("4")));
        data.add(new BeanWrapper(new BeanA("5")));
        data.add(new BeanWrapper(new BeanA("6")));
        data.add(new BeanWrapper(new BeanA("7")));
        data.add(new BeanWrapper(new BeanA("8")));
        data.add(new BeanWrapper(new BeanA("9")));
        data.add(new BeanWrapper(new BeanA("10")));
        data.add(new BeanWrapper(new BeanA("11")));
        return data;
    }

    private class BeanWrapper extends ItemWrapper<BeanA> implements View.OnClickListener, SwipeLayout.OnSwipeStateListener {

        private SwipeLayout swipeLayout;

        BeanWrapper(BeanA bean) {
            super(bean);
        }

        private boolean isOpen = false;

        @Override
        public int getLayoutRes() {
            return R.layout.item_swipe;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            View content = holder.findView(R.id.item_swipe_content);
            TextView tv = holder.findView(R.id.item_swipe_tv);
            ImageView img = holder.findView(R.id.item_swipe_img);
            View del = holder.findView(R.id.item_swipe_del);
            swipeLayout = holder.findView(R.id.item_swipe_drawer);
            if (isOpen) {
                swipeLayout.openDrawer();
            } else {
                swipeLayout.closeDrawer();
            }
            swipeLayout.setOnSwipeStateListener(this);

            tv.setText(bean.name);
            img.setImageResource(R.mipmap.ic_launcher);

            del.setOnClickListener(this);
            content.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_swipe_del:
                    adapter.remove(this);
                    break;
                case R.id.item_swipe_content:
                    if (isOpen) {
                        swipeLayout.closeDrawer();
                    } else {
                        swipeLayout.openDrawer();
                    }
                    isOpen = !isOpen;
                    break;
            }
        }

        @Override
        public void onSwipeStateChanged(boolean isOpen) {
            this.isOpen = isOpen;
        }
    }
}

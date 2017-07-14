package com.yushilei.commonapp.ui.test;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.bean.BeanC;
import com.yushilei.commonapp.common.widget.LoadingTextView;

import java.util.LinkedList;
import java.util.List;

public class TestActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private MultiRecyclerAdapter adapter;

    @Override
    protected void initView() {
        LoadingTextView loadingTextView = (LoadingTextView) findView(R.id.loading_tv);
        loadingTextView.startAni();
        mRecycler = findView(R.id.test_recycler);
        adapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(adapter);
        adapter.addAll(getWrapper());
    }

    public List<ItemWrapper> getWrapper() {
        List<ItemWrapper> data = new LinkedList<>();
        data.add(new BeanWrapperA(new BeanA("你好")));
        data.add(new BeanWrapperA(new BeanA("hello")));
        data.add(new BeanWrapperA(new BeanA("不会")));
        data.add(new BeanWrapperA(new BeanA("测试")));

        LinkedList<BeanA> subData = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            subData.add(new BeanA("item+" + i));
        }

        data.add(new BeanWrapperC(new BeanC("子Recycler", subData)));

        data.add(new BeanWrapperA(new BeanA("中间")));

        data.add(new BeanWrapperC(new BeanC("子Recycler2", subData)));
        return data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    public class BeanWrapperA extends ItemWrapper<BeanA> implements View.OnClickListener {
        public BeanWrapperA(BeanA bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_a_nest;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_a_nest_tv);
            tv.setText(bean.name);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showToast(bean.name);
        }
    }

    public class BeanWrapperC extends ItemWrapper<BeanC> {
        public BeanWrapperC(BeanC bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_b_nest_recycler;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView view = holder.findView(R.id.item_b_nest_tv);
            String text = "title=" + pos;
            view.setText(text);
            MultiRecyclerAdapter adapter = new MultiRecyclerAdapter();
            if (bean.list != null && bean.list.size() > 0) {
                List<ItemWrapper> data = new LinkedList<>();
                for (BeanA a : bean.list) {
                    data.add(new BeanWrapperA(a));
                }
                adapter.addAll(data);
            }
            RecyclerView subRecycler = holder.findView(R.id.item_b_nest_recycler);
            subRecycler.setAdapter(adapter);

        }
    }
}

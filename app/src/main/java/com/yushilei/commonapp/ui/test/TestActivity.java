package com.yushilei.commonapp.ui.test;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
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
    private ListView mLv;
    private MultiBaseAdapter adapter1;
    private View hideDialog;

    @Override
    public void initView() {
        LoadingTextView loadingTextView = (LoadingTextView) findView(R.id.loading_tv);
        loadingTextView.startAni();
        mRecycler = findView(R.id.test_recycler);
        adapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(adapter);
        adapter.addAll(getWrapper());
        mLv = findView(R.id.test_lv);
        adapter1 = new MultiBaseAdapter(3);
        mLv.setAdapter(adapter1);
        adapter1.addAll(getWrapper());
        hideDialog = findView(R.id.hide_dialog);
        setOnClick(R.id.show_recy, R.id.show_list);
        setOnClick(R.id.show_dialog, R.id.hide_dialog);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_recy:
                mRecycler.setVisibility(View.VISIBLE);
                mLv.setVisibility(View.GONE);
                break;
            case R.id.show_list:
                mRecycler.setVisibility(View.GONE);
                mLv.setVisibility(View.VISIBLE);
                break;
            case R.id.show_dialog:
                showLoadingDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialog.performClick();
                    }
                }, 5000);
                break;
            case R.id.hide_dialog:
                hideLoadingDialog();
                break;
        }
    }

    public List<ItemWrapper> getWrapper() {
        List<ItemWrapper> data = new LinkedList<>();
        data.add(new BeanWrapperA(new BeanA("你好")));
        data.add(new BeanWrapperA(new BeanA("hello")));

        data.add(new BeanWrapperB(new BeanB(10000)));

        data.add(new BeanWrapperA(new BeanA("不会")));
        data.add(new BeanWrapperA(new BeanA("测试")));

        LinkedList<BeanA> subData = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            subData.add(new BeanA("item+" + i));
        }

        data.add(new BeanWrapperC(new BeanC("子Recycler", subData)));

        data.add(new BeanWrapperA(new BeanA("中间")));
        data.add(new BeanWrapperB(new BeanB(10001)));

        data.add(new BeanWrapperC(new BeanC("子Recycler2", subData)));
        data.add(new BeanWrapperB(new BeanB(10001)));
        data.add(new BeanWrapperB(new BeanB(10002)));
        data.add(new BeanWrapperB(new BeanB(10003)));
        data.add(new BeanWrapperB(new BeanB(10004)));
        return data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    private class BeanWrapperA extends ItemWrapper<BeanA> implements View.OnClickListener {
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

    private class BeanWrapperB extends ItemWrapper<BeanB> implements View.OnClickListener {
        public BeanWrapperB(BeanB bean) {
            super(bean);
        }

        @Override
        public void onClick(View v) {
            adapter1.remove(this);
            adapter.remove(this);
            showToast("" + bean.age);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_b;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            ImageView img = holder.findView(R.id.item_b_img);
            TextView tv = holder.findView(R.id.item_b_tv);
            tv.setText(String.valueOf(bean.age + ""));
            img.setImageResource(R.mipmap.ic_head);
            holder.itemView.setOnClickListener(this);
        }
    }

    private class BeanWrapperC extends ItemWrapper<BeanC> {
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

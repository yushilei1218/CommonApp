package com.yushilei.commonapp.ui.loadmorerecycler;


import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.loadmore.LoadRecyclerView;
import com.yushilei.commonapp.common.loadmore.MultiHolderAdapter;
import com.yushilei.commonapp.common.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadMoreAdapterActivity extends BaseActivity {

    private LoadRecyclerView mRecyclerView;
    private MultiHolderAdapter mAdapter;
    private List data = new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_load_more_adapter;
    }

    @Override
    public void initView() {
        mRecyclerView = (LoadRecyclerView) findView(R.id.act_load_more_recycler);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = 20;
            }
        });
        mAdapter = new MultiHolderAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadMoreListener(new LoadRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                showToast("loadMore");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.loadFinish();
                        if (pos > 30) {
                            mRecyclerView.noMore();
                            mRecyclerView.loading();
                            mRecyclerView.loading();
                            mRecyclerView.loading();
                            mRecyclerView.noMore();
                            mRecyclerView.noMore();
                            mRecyclerView.loadFinish();
                            mRecyclerView.loading();

                        }
                    }
                }, 1000);

            }
        });
        mAdapter.setMatch(BeanA.class, new BeanDelegate());
        mAdapter.setMatch(BeanB.class, new BeanBDelegate());
        mAdapter.setRoot(data);
        getData();
        mAdapter.notifyDataSetChanged();

        setOnClick(R.id.remove_footer);
        setOnClick(R.id.no_more);
        setOnClick(R.id.add_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove_footer:
                mRecyclerView.loadFinish();
                showToast("remove");
                break;
            case R.id.no_more:
                mRecyclerView.noMore();
                showToast("no more");
                break;
            case R.id.add_data:
                getData();
                mAdapter.notifyDataSetChanged();
                showToast("add data");
                break;
        }
    }

    private int pos = 0;

    @SuppressWarnings("unchecked")
    private void getData() {
        List list = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int j = random.nextInt(10);
            int x = j % 2;
            pos++;
            if (x == 0) {
                list.add(new BeanB(pos));
            } else {
                list.add(new BeanA("第 xxx " + pos + "个"));
            }
        }
        data.addAll(list);
        String s = JsonUtil.toJson(data);
        Log.d(getTAG(), s);
    }

    public class BeanBDelegate extends HolderDelegate<BeanB> {

        @Override
        public int getLayoutId() {
            return R.layout.item_sub2;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanB beanB, int pos) {
            TextView tv = (TextView) holder.findView(R.id.sub_tv);
            String text = beanB.age + " 测试";
            tv.setText(text);
            tv.setTextColor(Color.RED);
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<BeanB> holder, BeanB beanB) {
            showToast(beanB.age + "");
            mAdapter.remove(beanB);
        }
    }

    public class BeanDelegate extends HolderDelegate<BeanA> {
        @Override
        public int getLayoutId() {
            return R.layout.item_sub;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            TextView tv = (TextView) holder.findView(R.id.sub_tv);
            tv.setText(beanA.name);
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<BeanA> holder, BeanA beanA) {
            showToast(beanA.name);
            mAdapter.remove(beanA);
        }
    }
}

package com.yushilei.commonapp.ui.loadmorerecycler;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.loadmore2.Load2RecyclerView;
import com.yushilei.commonapp.common.util.JsonUtil;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class Load2FootActivity extends BaseActivity {

    private Load2RecyclerView mRecyclerView;
    private MultiHolderAdapter mAdapter;
    private Handler handler = new Handler();
    private PtrFrameLayout mPtr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_load2_foot;
    }

    @Override
    public void initView() {
        setOnClick(R.id.add_data2);
        setOnClick(R.id.remove_data2);
        setOnClick(R.id.loading);
        setOnClick(R.id.remove_footer2);
        setOnClick(R.id.no_more2);
        mPtr = findView(R.id.act_load_2_ptr);
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.addPtrUIHandler(header);
        mPtr.setHeaderView(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });

        mRecyclerView = (Load2RecyclerView) findView(R.id.act_load_2_recy);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = 20;
            }
        });
        mAdapter = new MultiHolderAdapter();
        mAdapter.setMatch(BeanB.class, new BeanBDelegate());
        mAdapter.setMatch(BeanA.class, new BeanDelegate());
        mAdapter.setRoot(data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadMoreListener(new Load2RecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                refresh();
            }
        });

        getData();
        mAdapter.notifyDataSetChanged();
    }

    private void refresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initList();
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initList();
            }
        }, 3000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SetUtil.isEmpty(data)) {
                    Object o = data.get(data.size() - 1);
                    if (o instanceof BeanB) {
                        ((BeanB) o).age = 323232;
                    } else if (o instanceof BeanA) {
                        ((BeanA) o).name = "测测测测测";
                    }
                    mAdapter.update(o);
                }
            }
        }, 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.noMore();
            }
        }, 4000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initList();
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.loading();
                mPtr.refreshComplete();
            }
        }, 5000);
    }

    private void initList() {
        data.clear();
        Random random = new Random();
        int i = random.nextInt(10);
        for (int j = 0; j < i; j++) {
            int left = random.nextInt(10) % 2;
            pos++;
            if (left == 0) {
                data.add(new BeanB(pos));
            } else {
                data.add(new BeanA("第 xxx " + pos + "个"));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_data2:
                getData();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.remove_data2:
                data.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.loading:
                mRecyclerView.loading();
                break;
            case R.id.remove_footer2:
                mRecyclerView.loadFinish();
                break;
            case R.id.no_more2:
                mRecyclerView.noMore();
                break;
        }
    }

    private int pos;
    private List data = new ArrayList();

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

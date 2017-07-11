package com.yushilei.commonapp.ui.loadmorerecycler;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.widget.LoadMoreRecyclerView;
import com.yushilei.commonapp.common.widget.PtrCustomHeader;

import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class LoadMoreRecyclerActivity extends BaseActivity {

    private LoadMoreRecyclerView mRecycler;
    private MultiRecyclerAdapter mAdapter;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mCacheWrappers.size() >= 10) {
                        mRecycler.loadFinish();
                        mAdapter.addAllLast(mCacheWrappers);

                    } else {
                        mAdapter.addAllLast(mCacheWrappers);
                        mRecycler.noMore();
                    }

                    break;
                case 2:
                    mPtr.refreshComplete();
                    mAdapter.addAll(mCacheWrappers);
                    break;
            }
            return true;
        }
    });
    private PtrFrameLayout mPtr;

    @Override
    protected void initView() {

        View mLoadV = findView(R.id.loading_layout);
        mRecycler = findView(R.id.load_more_recycler);

        mPtr = findView(R.id.load_more_ptr);
        PtrCustomHeader header = new PtrCustomHeader(this);
        mPtr.addPtrUIHandler(header);
        mPtr.setHeaderView(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadFirstPage();
            }
        });

        setOnClick(R.id.btn_load_finish, R.id.btn_no_more);
        mAdapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(mAdapter);


        mRecycler.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i(getTAG(), "onLoadMore");
                doLoadMore();
            }
        });
        mPtr.autoRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_finish:
                mRecycler.loadFinish();
                break;
            case R.id.btn_no_more:
                mRecycler.noMore();
                break;
        }
    }

    private void loadFirstPage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                pos = 0;
                mCacheWrappers = getListWrappers();
                mHandler.sendEmptyMessage(2);
            }
        }).start();
    }

    private void doLoadMore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mCacheWrappers = getListWrappers();
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_load_more_recycler;
    }

    private int pos = 0;
    private List<ItemWrapper> mCacheWrappers;

    public List<ItemWrapper> getListWrappers() {
        SystemClock.sleep(2000);
        int index = 10;
        if (pos > 20)
            index = 5;
        List<ItemWrapper> data = new LinkedList<>();
        for (int i = 0; i < index; i++) {
            if (i % 2 == 1) {
                data.add(new BeanWrapperB(new BeanB(pos)));
            } else {
                data.add(new BeanWrapper(new BeanA("item+  " + pos)));
            }

            pos++;
        }
        return data;
    }

    /*
    数据包装
     */
    private class BeanWrapper extends ItemWrapper<BeanA> {
        private BeanWrapper(BeanA bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_a;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView tv = holder.findView(R.id.item_a_tv);
            ImageView img = holder.findView(R.id.item_a_img);
            String text = "Name=" + bean.name;
            tv.setText(text);
            img.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    private class BeanWrapperB extends ItemWrapper<BeanB> implements View.OnClickListener {
        public BeanWrapperB(BeanB bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_b;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView view = holder.findView(R.id.item_b_tv);
            ImageView img = holder.findView(R.id.item_b_img);

            String text = bean.age + " 岁";
            view.setText(text);
            img.setImageResource(R.mipmap.ic_launcher);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdapter.remove(this);
            showToast("Age=" + bean.age);
        }
    }
}

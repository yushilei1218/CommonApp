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

import java.util.LinkedList;
import java.util.List;

public class LoadMoreRecyclerActivity extends BaseActivity {

    private LoadMoreRecyclerView mRecycler;
    private MultiRecyclerAdapter mAdapter;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mCacheWrappers.size() >= 3) {
                        mRecycler.loadFinish();
                        mAdapter.addAllLast(mCacheWrappers);

                    } else {
                        mAdapter.addAllLast(mCacheWrappers);
                        mRecycler.noMore();
                    }

                    break;
            }
            return true;
        }
    });

    @Override
    protected void initView() {

        mRecycler = findView(R.id.load_more_recycler);
        setOnClick(R.id.btn_load_finish, R.id.btn_no_more);
        mAdapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(mAdapter);

        doLoadMore();
        mRecycler.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i(getTAG(), "onLoadMore");
                doLoadMore();
            }
        });
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
        int index = 3;
        if (pos > 10)
            index = 1;
        List<ItemWrapper> data = new LinkedList<>();
        for (int i = 0; i < index; i++) {
            data.add(new BeanWrapper(new BeanA("item+  " + pos)));
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
}

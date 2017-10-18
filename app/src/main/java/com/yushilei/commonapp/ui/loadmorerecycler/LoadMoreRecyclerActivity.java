package com.yushilei.commonapp.ui.loadmorerecycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
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

public class LoadMoreRecyclerActivity extends BaseActivity {

    private LoadMoreRecyclerView mRecycler;
    private MultiRecyclerAdapter mAdapter;
    private PtrFrameLayout mPtr;

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
                    mRecycler.loadFinish();
                    break;
            }
            return true;
        }
    });


    @Override
    public void initView() {

        mRecycler = findView(R.id.load_more_recycler);
        mPtr = findView(R.id.load_more_ptr);

        mAdapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(mAdapter);
        /*RecyclerView Item画分割线*/
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                RecyclerView.LayoutManager manager = parent.getLayoutManager();
                int count = manager.getChildCount();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1f);
                paint.setColor(Color.parseColor("#cccccc"));
                for (int i = 0; i < count; i++) {
                    View child = manager.getChildAt(i);
                    if (i == 0) {
                        c.drawLine(child.getLeft(), child.getTop(), child.getRight(), child.getTop(), paint);
                    }
                    c.drawLine(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom(), paint);
                }
            }
        });
        /*设置上拉加载更多监听器*/
        mRecycler.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i(getTAG(), "onLoadMore");
                loadMore();
            }
        });
        /*设置下拉刷新Header*/
        PtrCustomHeader header = new PtrCustomHeader(this);
        mPtr.addPtrUIHandler(header);
        mPtr.setHeaderView(header);
        /*设置触发刷新时回调*/
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadFirstPage();
            }
        });

        setOnClick(R.id.btn_load_finish, R.id.btn_no_more);

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

    private void loadMore() {
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
        if (pos > 20) {
            index = 5;
        }
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

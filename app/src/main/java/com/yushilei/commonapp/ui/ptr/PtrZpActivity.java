package com.yushilei.commonapp.ui.ptr;

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
import com.yushilei.commonapp.common.widget.PtrFirstHeader;
import com.yushilei.commonapp.common.widget.drop.DropCover;
import com.yushilei.commonapp.common.widget.drop.DropFake;
import com.yushilei.commonapp.common.widget.drop.DropManager;

import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PtrZpActivity extends BaseActivity {


    private MultiRecyclerAdapter adapter;

    private static final int REFRESHING = 0X01;
    private static final int LOAD_MORE = 0X02;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESHING:
                    mRecycler.setCanLoadMore(true);
                    mPtr.refreshComplete();
                    mRecycler.loadFinish();
                    adapter.addAll(mCacheList);
                    break;
                case LOAD_MORE:
                    if (mCacheList.size() < 9) {
                        adapter.addAllLast(mCacheList);
                        mRecycler.noMore();
                    } else {
                        mRecycler.loadFinish();
                        adapter.addAllLast(mCacheList);
                    }

                    break;
            }
            return true;
        }
    });
    private LoadMoreRecyclerView mRecycler;
    private PtrFrameLayout mPtr;
    private DropCover.IDropCompletedListener mDropCompletedListener;

    @Override
    public void initView() {
        mPtr = findView(R.id.ptr_zp_ptr);
        mRecycler = findView(R.id.ptr_load_more_recycler);

        adapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(adapter);
        mRecycler.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                refreshing(LOAD_MORE);
            }
        });

        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.setHeaderView(header);
        mPtr.addPtrUIHandler(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mRecycler.setCanLoadMore(false);
                refreshing(REFRESHING);
            }
        });
        mDropCompletedListener = new DropCover.IDropCompletedListener() {
            @Override
            public void onCompleted(Object id, boolean explosive) {
                Log.i(getTAG(), "Object=" + id + " explosive=" + explosive);
            }
        };
        DropManager.getInstance().init(this, (DropCover) findView(R.id.ptr_zp_drop_cover), mDropCompletedListener);
        registerDropListener(true);
    }

    @Override
    protected void onDestroy() {
        registerDropListener(false);
        super.onDestroy();
    }

    private void registerDropListener(boolean is) {
        if (is) {
            DropManager.getInstance().getDropCover().addDropCompletedListener(mDropCompletedListener);
        } else {
            DropManager.getInstance().getDropCover().removeDropCompletedListener(mDropCompletedListener);
        }

    }

    int pos = 0;
    private List<ItemWrapper> mCacheList;

    private void refreshing(final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                mCacheList = new LinkedList<>();
                int index = 10;
                if (pos > 20) {
                    index = 3;
                }
                for (int i = 0; i < index; i++) {
                    if (i % 2 == 1) {
                        mCacheList.add(new BeanWrapper(new BeanA("name= " + pos)));
                    } else {
                        mCacheList.add(new BeanWrapperB(new BeanB(pos)));
                    }
                    pos++;
                }
                handler.sendEmptyMessage(type);
            }
        }).start();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ptr_zp;
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

    private class BeanWrapperB extends ItemWrapper<BeanB> implements View.OnClickListener, DropFake.ITouchListener {
        public BeanWrapperB(BeanB bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_drop;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView view = holder.findView(R.id.item_drop_tv);
            DropFake img = holder.findView(R.id.item_drop_fake);

            String text = bean.age + " 岁";
            view.setText(text);
            img.setText("11+");
            img.setClickListener(this);

            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapter.remove(this);
            showToast("Age=" + bean.age);
        }

        /*设置红点拖拽监听*/
        @Override
        public void onDown() {
            DropFake fakeView = findView(R.id.item_drop_fake);
            DropManager.getInstance().getDropCover().down(fakeView, fakeView.getText().toString());
        }

        @Override
        public void onMove(float curX, float curY) {
            DropManager.getInstance().getDropCover().move(curX, curY);
        }

        @Override
        public void onUp() {
            DropManager.getInstance().getDropCover().up();
        }
    }
}

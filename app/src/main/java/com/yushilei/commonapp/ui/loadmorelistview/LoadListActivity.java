package com.yushilei.commonapp.ui.loadmorelistview;


import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.widget.LoadListView;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class LoadListActivity extends BaseActivity {
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPtr.refreshComplete();
                    mAdapter.replaceData((List) msg.obj);

                    break;
                case 2:
                    if (msg.obj == null) {
                        mLoadLv.noMore();
                    } else {
                        mLoadLv.loadFinish();
                        mAdapter.addAll((List) msg.obj);
                    }
                    break;
            }
            return true;
        }
    });
    private LoadListView mLoadLv;
    private MultiListAdapter mAdapter;
    private PtrFrameLayout mPtr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_load_list;
    }

    @Override
    public void initView() {
        mPtr = findView(R.id.load_list_ptr);
        mLoadLv = findView(R.id.load_list_lv);
        mAdapter = new MultiListAdapter(1);
        mLoadLv.setAdapter(mAdapter);
        mAdapter.setMatch(BeanA.class, new BeanDelegate());
        mLoadLv.setLoadMoreListener(new LoadListView.OnLoadMoreListener() {
            @Override
            public void onLoadingMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        Message msg = new Message();
                        msg.what = 2;
                        page++;
                        msg.obj = getData(page);
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.addPtrUIHandler(header);
        mPtr.setHeaderView(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新
                page = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        Message msg = new Message();
                        msg.what = 1;
                        page++;
                        msg.obj = getData(page);
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });
    }

    int page = 0;

    private List<BeanA> getData(int page) {
        if (page < 3) {
            List<BeanA> data = new ArrayList<>();
            data.add(new BeanA("你好"));
            data.add(new BeanA("呵呵"));
            data.add(new BeanA("哟哟"));
            return data;
        } else {
            return null;
        }
    }

    private final class BeanDelegate extends HolderDelegate<BeanA> {
        @Override
        public int getLayoutId() {
            return R.layout.item_a;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_a_tv);
            ImageView img = (ImageView) holder.findView(R.id.item_a_img);
            tv.setText(beanA.name);
            img.setImageResource(R.mipmap.ic_clock);
        }
    }
}

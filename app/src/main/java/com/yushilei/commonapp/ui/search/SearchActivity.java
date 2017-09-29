package com.yushilei.commonapp.ui.search;


import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class SearchActivity extends BaseActivity {

    private PtrFrameLayout mPtr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        RecyclerView recyclerView = findView(R.id.act_search_recycler);
        mPtr = findView(R.id.act_search_ptr);
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.setHeaderView(header);
        mPtr.addPtrUIHandler(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtr.refreshComplete();
                    }
                }, 1000);
            }
        });
        MultiHolderAdapter adapter = new MultiHolderAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setMatch(BeanB.class, new BeanDelegate());
        adapter.addAll(getList());
    }

    private List<BeanB> getList() {
        List<BeanB> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new BeanB(i));
        }
        return data;
    }

    private final class BeanDelegate extends HolderDelegate<BeanB> {

        @Override
        public int getLayoutId() {
            return R.layout.item_b;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanB beanB, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_b_tv);
            String text = beanB.age + " 岁";
            tv.setText(text);
        }
    }
}

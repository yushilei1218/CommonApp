package com.yushilei.commonapp.ui.vlayout;


import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class VLayoutActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vlayout;
    }

    @Override
    public void initView() {
        RecyclerView recycler = findView(R.id.act_v_layout_recycler);
        VirtualLayoutManager vlm = new VirtualLayoutManager(this);
        recycler.setLayoutManager(vlm);
    }
}

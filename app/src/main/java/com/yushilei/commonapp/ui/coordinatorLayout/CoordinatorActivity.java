package com.yushilei.commonapp.ui.coordinatorLayout;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.delegate.BeanADelegate;

import java.util.ArrayList;

public class CoordinatorActivity extends BaseActivity {


    private View mJobV;
    private MenuPopWindow mPop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinator;
    }

    @Override
    public void initView() {

        mJobV = findView(R.id.act_coordinator_job);
        RecyclerView recyclerView = findView(R.id.act_coordinator_recycler);
        MultiHolderAdapter adapter = new MultiHolderAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setMatch(BeanA.class, new BeanADelegate());
        ArrayList<BeanA> newData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            newData.add(new BeanA(i + ""));
        }
        adapter.addAll(newData);
        setOnClick(mJobV);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_coordinator_job:
                if (mPop == null) {
                    mPop = new MenuPopWindow(this);
                }
                if (mPop.isShowing()) {
                    mPop.dismiss();
                } else {
                    mPop.showAsDropDown(mJobV);
                }
                break;
            default:
                break;
        }
    }
}

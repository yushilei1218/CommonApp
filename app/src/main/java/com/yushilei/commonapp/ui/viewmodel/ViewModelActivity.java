package com.yushilei.commonapp.ui.viewmodel;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yushilei.commonapp.R;

import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.util.SetUtil;

import java.util.ArrayList;
import java.util.List;

public class ViewModelActivity extends BaseActivity {

    private ArrayList<String> mData;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_model;
    }

    @Override
    public void initView() {
        ListView lv = findView(R.id.act_view_model_lv);
        mData = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
        lv.setAdapter(mAdapter);
        setOnClick(R.id.act_view_model_btn);


        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                Log.d(getTAG(), "onChanged");
                if (SetUtil.isEmpty(strings)) {
                    return;
                }
                mData.addAll(strings);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_view_model_btn:
                ViewModelFragment fg = new ViewModelFragment();
                fg.show(getSupportFragmentManager(), "show");
                break;
        }
    }
}

package com.yushilei.commonapp.ui.sharedelement;


import android.os.Bundle;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class SharedElementActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared_element;
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new GridFragment()).commit();
    }
}

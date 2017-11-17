package com.yushilei.commonapp.ui.constraintlayout;


import android.content.Intent;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

/**
 * 测试约束布局
 *
 * @author shilei.yu
 */
public class ConstraintActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_constraint;
    }

    @Override
    public void initView() {
        setOnClick(R.id.btn5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn5:
                startActivity(new Intent(this, ConstraintSetActivity.class));
                break;
        }
    }
}

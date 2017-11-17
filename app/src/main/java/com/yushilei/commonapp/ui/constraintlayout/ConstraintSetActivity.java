package com.yushilei.commonapp.ui.constraintlayout;


import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class ConstraintSetActivity extends BaseActivity {

    private ConstraintSet first = new ConstraintSet();
    private ConstraintSet second = new ConstraintSet();
    boolean isFirst = false;
    private ConstraintLayout mFirstLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_constraint_set;
    }

    @Override
    public void initView() {
        setOnClick(R.id.act_constraint_set_btn);
        setOnClick(R.id.act_constraint_set_btn2);
        mFirstLayout = findView(R.id.act_constraint_set_layout);
        first.clone(mFirstLayout);
        second.clone(this, R.layout.activity_constraint_set2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_constraint_set_btn:
                if (isFirst) {
                    TransitionManager.beginDelayedTransition(mFirstLayout);// 动画效果
                    second.applyTo(mFirstLayout);
                } else {
                    TransitionManager.beginDelayedTransition(mFirstLayout);
                    first.applyTo(mFirstLayout);
                }
                isFirst = !isFirst;
                break;
            case R.id.act_constraint_set_btn2:
                showToast("BTN2 被点击");
                Button button =  findView(R.id.act_constraint_set_btn2);
                button.setText("BTN2 被点击");
                break;
        }
    }
}

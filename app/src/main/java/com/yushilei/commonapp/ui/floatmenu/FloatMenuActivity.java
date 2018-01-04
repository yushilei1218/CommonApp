package com.yushilei.commonapp.ui.floatmenu;


import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class FloatMenuActivity extends BaseActivity {

    private FloatingActionsMenu mMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_float_menu;
    }

    @Override
    public void initView() {
        mMenu = findView(R.id.float_menu);
        FloatingActionButton floatBtn = findView(R.id.float_btn);
        setOnClick(floatBtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_btn:
                if (mMenu.isExpanded()) {
                    mMenu.collapse();
                } else {
                    mMenu.expand();
                }
                break;
            default:
                break;
        }
    }
}

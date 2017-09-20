package com.yushilei.commonapp.ui.tab;


import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class TabsActivity extends BaseActivity {

    private RadioGroup mTabGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tabs;
    }

    @Override
    public void initView() {
        mTabGroup = findView(R.id.main_tab);
        mTabGroup.check(R.id.tab1);

        mTabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                show(checkedId);
            }
        });
        setOnClick(R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        show(id);
    }

    private void show(int id) {

        String text = "";
        switch (id) {
            case R.id.tab1:
                text = "tab1";
                break;
            case R.id.tab2:
                text = "tab2";
                break;
            case R.id.tab3:
                text = "tab3";
                break;
            case R.id.tab4:
                text = "tab4";
                break;
        }
        showToast(text + "被点击");
    }
}

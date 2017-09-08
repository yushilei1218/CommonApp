package com.yushilei.commonapp.ui.scroll;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.util.DensityUtil;

public class ScrollActivity extends BaseActivity {

    private int keyHeight;
    private LinearLayout mLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll;
    }

    @Override
    public void initView() {
        mLayout = findView(R.id.scroll_layout);
        ScrollView mScroll = findView(R.id.scroll_root);
        mLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    showToast("键盘弹起");
                    _MoveUp(mLayout, 40);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    showToast("键盘落下");
                    _MoveDown(mLayout, 40);
                }
            }
        });
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
    }
    /**
     * 布局上移
     */
    void _MoveUp(View v, int h) {
        v.scrollBy(0, DensityUtil.dip2px(this, h));
    }


    /**
     * 布局下沉
     *
     * @param v
     * @param h
     */
    void _MoveDown(View v, int h) {
        v.scrollBy(0, -DensityUtil.dip2px(this, h));
    }
}

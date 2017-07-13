package com.yushilei.commonapp.ui.test;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected void initView() {
        TextView tv = (TextView) findView(R.id.continue_talk);

        Drawable drawable1 = getResources().getDrawable(R.drawable.rotate_drawable);
        drawable1.setBounds(new Rect(0,0,40,40));
        tv.setCompoundDrawables(drawable1, null, null, null);
        Drawable[] drawables = tv.getCompoundDrawables();

        RotateDrawable drawable = (RotateDrawable) drawables[0];

        ObjectAnimator ani = ObjectAnimator.ofInt(drawable, "Level", 1, 10000);
        ani.setRepeatCount(-1);
        ani.setDuration(1000);
        ani.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }
}

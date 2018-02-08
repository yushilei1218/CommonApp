package com.yushilei.commonapp.ui.feizhu.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public class MoveLinearLayout extends LinearLayout {

    private ObjectAnimator mShowAni;
    private ObjectAnimator mHideAni;
    private boolean isHide = true;

    public MoveLinearLayout(Context context) {
        super(context);
    }

    public MoveLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show() {
        if (!isHide) {
            return;
        }
        int height = getHeight();
        if (mHideAni != null) {
            mHideAni.cancel();
        }
        if (mShowAni == null) {
            mShowAni = ObjectAnimator.ofFloat(this, "Y", -height, 0);
            mShowAni.setDuration(200);
        }
        mShowAni.setFloatValues(-height, 0);
        isHide = false;
        mShowAni.start();
    }

    public void hide() {
        if (isHide) {
            return;
        }
        int height = getHeight();
        if (mShowAni != null) {
            mShowAni.cancel();
        }
        if (mHideAni == null) {
            mHideAni = ObjectAnimator.ofFloat(this, "Y", 0, -height);
            mHideAni.setDuration(200);
        }
        mHideAni.setFloatValues(0, -height);
        isHide = true;
        mHideAni.start();
    }
}

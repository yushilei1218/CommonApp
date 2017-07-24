package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 */

public class FixPtrFrameLayout extends PtrFrameLayout {
    private static final String TAG = "FixPtrFrameLayout";
    private boolean disallowIntercept = false;

    public FixPtrFrameLayout(Context context) {
        super(context);
    }

    public FixPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.i(TAG, "requestDisallowInterceptTouchEvent " + disallowIntercept);
        this.disallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            disallowIntercept = false;
        }
        if (disallowIntercept) {
            return dispatchTouchEventSupper(e);
        } else {
            return super.dispatchTouchEvent(e);
        }
    }
}

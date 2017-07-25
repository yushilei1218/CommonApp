package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 修复PtrFrameLayout 在子view 调用
 * {@link android.view.ViewGroup#requestDisallowInterceptTouchEvent(boolean)}
 * 时，PtrFrameLayout无处理事件冲突的问题
 * <p>
 * 解决方法，记录当前requestDisallowInterceptTouchEvent boolean值:disallowIntercept；
 * 在dispatchTouchEvent 根据 disallowIntercept 进行判断是否允许PtrFrameLayout拦截事件
 *
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
        this.disallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        int action = e.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            disallowIntercept = false;
        }
        if (disallowIntercept) {
            return dispatchTouchEventSupper(e);
        } else {
            return super.dispatchTouchEvent(e);
        }
    }
}

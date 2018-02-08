package com.yushilei.commonapp.ui.feizhu.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public class ScrollBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "ScrollBehavior";
    private View mHeader;

    public ScrollBehavior() {
    }

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        log("onStartNestedScroll " + child + " " + target);
        if (mHeader == null) {
            mHeader = coordinatorLayout.findViewById(R.id.act_feizhu_header);
        }
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        int offset = mHeader == null ? 0 : mHeader.getHeight();
        int top = (int) child.getY();
        if (dy > 0) {
            //向上滑
            if (top > 0) {
                int i = top - dy;
                int temp = i >= 0 ? i : 0;
                ViewCompat.setY(child, temp);
                consumed[1] = dy;
            }
        } else {
            //向下滑
            if (top < offset) {
                int i = top - dy;
                int temp = i >= offset ? offset : i;
                ViewCompat.setY(child, temp);
                consumed[1] = dy;
            }
        }
        log("onNestedPreScroll " + "dy=" + dy + " offset=" + offset + " top=" + top);
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }
}

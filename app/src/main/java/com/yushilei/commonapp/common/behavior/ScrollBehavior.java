package com.yushilei.commonapp.common.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.widget.Map2View;

/**
 * @author shilei.yu
 * @since on 2017/7/19.
 */

public class ScrollBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {
    public ScrollBehavior() {
    }

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RelativeLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return target.getId()== R.id.map_recycler && nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RelativeLayout child, View target, int dx, int dy, int[] consumed) {
        int offset = getOffset(coordinatorLayout, child, target);
        RecyclerView mTarget = (RecyclerView) target;
        RecyclerView.LayoutManager layoutManager = mTarget.getLayoutManager();

        int childCount = layoutManager.getChildCount();
        int curY = (int) child.getY();
        Log.i("Behavior", "dy=" + dy);
        if (dy >= 0) {//上滑
            if (childCount <= 0) {
                return;
            }
            if (-curY == offset) {
                return;
            }
            consumed[1] = dy;
            int move = -curY + dy >= offset ? -offset : curY - dy;
            child.setY(move);
        } else {//下滑
            if (curY >= 0) {
                return;
            }
            if (childCount <= 0) {
                return;
            }
            View child0 = layoutManager.getChildAt(0);
            int position = layoutManager.getPosition(child0);

            if (position == 0 && child0.getTop() >= 0) {
                float move = curY - dy <= 0 ? curY - dy : 0;
                consumed[1] = dy;
                child.setY(move);
            }
        }
    }

    private int getOffset(CoordinatorLayout coordinatorLayout, RelativeLayout child, View target) {
        int containerHeight = coordinatorLayout.getHeight();
        int mapHeight = child.getHeight();
        int recyclerHeight = target.getHeight();
        return mapHeight + recyclerHeight - containerHeight;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, RelativeLayout child, View target) {
        //嵌套滑动结束
        int offset = getOffset(coordinatorLayout, child, target);
        int curY = (int) child.getY();
        boolean b = Math.abs(curY) >= offset * 0.5f;
        if (b) {//上弹
            ObjectAnimator ani = ObjectAnimator.ofFloat(child, "Y", curY, -offset);
            ani.setInterpolator(new AccelerateInterpolator());
            ani.setDuration(300);
            ani.start();
        } else {//下弹
            ObjectAnimator ani = ObjectAnimator.ofFloat(child, "Y", curY, 0);
            ani.setInterpolator(new AccelerateInterpolator());
            ani.setDuration(200);
            ani.start();
        }
    }
}

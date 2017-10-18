package com.yushilei.commonapp.common.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.yushilei.commonapp.R;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author shilei.yu
 * @since 2017/9/30
 */

public class SearchScrollBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private static final String TAG = "SearchScrollBehavior";
    private PtrFrameLayout mPtr;
    private ObjectAnimator mHideAnimator;
    private ObjectAnimator mShowAnimator;
    private int mHeight;

    public SearchScrollBehavior() {
    }

    public SearchScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return target.getId() == R.id.act_search_recycler && nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, LinearLayout child, View target, int dx, int dy, int[] consumed) {
        if (mPtr == null) {
            mPtr = (PtrFrameLayout) coordinatorLayout.findViewById(R.id.act_search_ptr);
        }
        mHeight = child.getHeight();
        int curY = (int) mPtr.getY();
        if (dy > 0) {//上滑
            if (curY > 0) {
                consumed[1] = dy;
                float newY = curY - dy;
                if (newY < 0) {
                    newY = 0;
                }
                mPtr.setY(newY);
            }
        } else {//下滑
            if (curY < mHeight) {
                consumed[1] = dy;
                float newY = curY - dy;
                if (newY > mHeight) {
                    newY = mHeight;
                }
                mPtr.setY(newY);
            }
        }
        if (curY == 0 && dy > 0) {
            if (isHide) {
                return;
            }
            //隐藏
            if (mShowAnimator != null && mShowAnimator.isRunning()) {
                mShowAnimator.cancel();
            }
            if (mHideAnimator != null && mHideAnimator.isRunning()) {
                return;
            }
            hide(child);
        }
        if (curY > 10 && dy < 0) {
            if (!isHide) {
                return;
            }
            if (mHideAnimator != null && mHideAnimator.isRunning()) {
                mHideAnimator.cancel();
            }
            if (mShowAnimator != null && mShowAnimator.isRunning()) {
                return;
            }
            show(child);
        }

        Log.d(TAG, "onNestedPreScroll ptr=" + curY + " height=" + mHeight);
    }

    private boolean isHide = false;

    private void cancel() {

    }

    private void hide(View target) {
        if (mHideAnimator == null) {
            mHideAnimator = ObjectAnimator.ofFloat(target, "Y", 0, -target.getHeight());
            mHideAnimator.setDuration(200);
        }
        isHide = true;
        mHideAnimator.start();
    }

    private void show(View target) {
        if (mShowAnimator == null) {
            mShowAnimator = ObjectAnimator.ofFloat(target, "Y", -target.getHeight(), 0);
            mShowAnimator.setDuration(200);
        }
        isHide = false;
        mShowAnimator.start();
    }
}

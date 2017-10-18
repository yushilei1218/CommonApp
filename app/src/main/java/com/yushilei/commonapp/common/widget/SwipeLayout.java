package com.yushilei.commonapp.common.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Scroller;

/**
 * 自定义抽屉式布局
 * <p>
 * 如需触发抽屉模式，当前SwipeLayout必须包含2个子View，并且2个子View的实际宽度之和需大于当前View
 * <p>
 * 使用注意：
 * swipeLayout的布局高度默认==子view中最高的那个
 * getChild(0) 会被默认当做主显示视图；
 * getChild(1) 会被默认当做可拉出来的抽屉视图；
 *
 * @author shilei.yu
 * @since on 2017/7/28.
 */

public class SwipeLayout extends ViewGroup {
    private static final String TAG = "SwipeLayout";

    private static final int MAX_ANI_TIME = 300;
    /**
     * 判定当前SwipeLayout是否有被当成抽屉的潜能 ：isSwipeCapacity=getChildCount==2 && getMaxScrollX >0 ;
     */
    private boolean isSwipeCapacity = false;

    private int mTouchSlop;
    /**
     * 当前SwipeLayout是否处于打开状态
     */
    private boolean isOpen = false;

    private OnSwipeStateListener onSwipeStateListener;

    public void setOnSwipeStateListener(OnSwipeStateListener onSwipeStateListener) {
        this.onSwipeStateListener = onSwipeStateListener;
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration con = ViewConfiguration.get(context);
        mTouchSlop = con.getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        if (count == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int height = 0;
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int measuredHeight = child.getMeasuredHeight();
                if (measuredHeight > height) {
                    height = measuredHeight;
                }
            }
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count == 1) {
            View child0 = getChildAt(0);
            child0.layout(0, 0, child0.getMeasuredWidth(), child0.getMeasuredHeight());
        } else if (count == 2) {
            isSwipeCapacity = getMaxScrollX() > 0;

            View child0 = getChildAt(0);
            int cR = child0.getMeasuredWidth();
            child0.layout(0, 0, cR, child0.getMeasuredHeight());

            View child1 = getChildAt(1);
            child1.layout(cR, 0, cR + child1.getMeasuredWidth(), child1.getMeasuredHeight());

            if (getMaxScrollX() > 0) {
                if (isOpen) {
                    doOpenDrawer();
                }
            }
        } else {
            throw new RuntimeException("SwipeLayout 最多支持2个Child");
        }
    }

    private float mLastX;
    private float mLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isSwipeCapacity) {
            return false;
        }

        float x = ev.getX();
        float y = ev.getY();
        boolean isIntercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                /*当横向滑动的距离大于竖向滑动 && 横向滑动大于系统默认slop时 触发拦截*/
                float offsetX = Math.abs(x - mLastX);
                if (offsetX > Math.abs(y - mLastY) && offsetX >= mTouchSlop) {
                    isIntercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return isIntercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*要求parent不要拦截当前view*/
        requestDisallowInterceptTouchEvent(true);

        /*处理滑动*/
        float x = event.getX();
        processScroll(x);

        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                //弹性滑动
                boolean isScrollLeft = Math.abs(getScrollX()) >= 0.5f * getMaxScrollX();
                if (isScrollLeft) {
                    isOpen = true;
                    //拉开Drawer
                    startAnimation(getMaxScrollX());
                } else {
                    isOpen = false;
                    //关闭Drawer
                    startAnimation(0);
                }
                if (onSwipeStateListener != null) {
                    onSwipeStateListener.onSwipeStateChanged(isOpen);
                }
            }
            break;
        }
        mLastX = x;
        return true;
    }

    /**
     * 结合mLastX 处理本次需要滚动的距离，并完成滚动
     *
     * @param x 本次滑动的x
     */
    private void processScroll(float x) {
        int off = (int) (mLastX - x);

        int offset = getScrollX() + off;
        if (offset <= 0) {
            scrollTo(0, 0);
            return;
        }
        int realOffset = offset > getMaxScrollX() ? getMaxScrollX() : offset;
        scrollTo(realOffset, 0);
    }

    private void startAnimation(int destinationX) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "ScrollX", getScrollX(), destinationX);
        animator.setInterpolator(new AccelerateInterpolator());
        long time = (long) (MAX_ANI_TIME * (Math.abs((float) getScrollX() - destinationX) / getMaxScrollX()));
        if (time < 50) {
            time = 50;
        }
        animator.setDuration(time);

        animator.start();
    }

    /**
     * 获取横向最大滚动距离
     *
     * @return 横向最大滚动距离
     */
    private int getMaxScrollX() {
        return getInnerTotalWidth() - getMeasuredWidth();
    }

    /**
     * 获取子View 宽度总和
     */
    private int getInnerTotalWidth() {
        return getChildAt(0).getMeasuredWidth() + getChildAt(1).getMeasuredWidth();
    }

    public void openDrawer() {
        Log.i(TAG, "openDrawer");
        isOpen = true;
        if (getChildCount() == 2 && getMaxScrollX() > 0) {
            doOpenDrawer();
        }
    }

    private void doOpenDrawer() {
        View content = getChildAt(0);
        View drawer = getChildAt(1);
        int width = getWidth();
        int contentWidth = content.getMeasuredWidth();
        int drawerWidth = drawer.getMeasuredWidth();
        scrollTo(contentWidth + drawerWidth - width, 0);
    }

    public void closeDrawer() {
        isOpen = false;
        scrollTo(0, 0);
    }

    public interface OnSwipeStateListener {
        void onSwipeStateChanged(boolean isOpen);
    }
}

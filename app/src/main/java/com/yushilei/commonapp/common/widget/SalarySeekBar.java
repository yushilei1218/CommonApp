package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.bean.base.IFocus;

/**
 * @author shilei.yu
 * @since 2017/8/31
 */

public class SalarySeekBar extends View {
    private static final String TAG = "SalarySeekBar";

    private final float widthHeightRate = 0.2f;

    private int mWidth;
    private int mHeight;
    private Drawable mCursorLeft;
    private Drawable mCursorRight;
    private Rect mLeftRect;
    private Rect mRightRect;

    public SalarySeekBar(Context context) {
        this(context, null);
    }

    public SalarySeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mCursorLeft = context.getResources().getDrawable(R.mipmap.range_seekbar_thumb_indicator);
        mCursorRight = context.getResources().getDrawable(R.mipmap.range_seekbar_thumb_indicator);

        mLeftRect = new Rect();
        mRightRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int intrinsicWidth = mCursorLeft.getIntrinsicWidth();
        int intrinsicHeight = mCursorLeft.getIntrinsicHeight();
        mWidth = w;
        mHeight = h;
        mLeftRect.set(0, 0, intrinsicWidth, intrinsicHeight);
        mRightRect.set(0, 0, intrinsicWidth, intrinsicHeight);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            throw new RuntimeException("宽度必须固定");
        }
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int newHSpec = MeasureSpec.makeMeasureSpec((int) (size * widthHeightRate), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        mLeftRect.offset(100, 0);
        mRightRect.offset(200, 0);
        mCursorLeft.setBounds(mLeftRect);
        mCursorLeft.draw(canvas);

        mCursorLeft.setBounds(mRightRect);
        mCursorLeft.draw(canvas);
    }

    private State curState;
    private float lastX;
    private float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curState = checkTouchState(event);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                curState = State.NONE;
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

    private State checkTouchState(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (mLeftRect.contains(x, y)) {
            return State.LEFT;
        }
        if (mRightRect.contains(x, y)) {
            return State.RIGHT;
        }
        return State.NONE;
    }

    private enum State {
        LEFT,
        RIGHT,
        NONE
    }
}

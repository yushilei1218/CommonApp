package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yushilei.commonapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/9.
 */

public class SeekBar extends View {

    private int mPhase = 0;

    private List<PointF> units = new ArrayList<>();

    private Drawable mLeft;
    private Drawable mRight;

    private Rect mLeftRect = new Rect();
    private Rect mRightRect = new Rect();

    private boolean isDragging = false;

    private Rect mTemp;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public SeekBar(Context context) {
        this(context, null);
    }

    public SeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SeekBar);
        mPhase = ta.getInt(R.styleable.SeekBar_phase, 0);
        mLeft = ta.getDrawable(R.styleable.SeekBar_leftindicater);
        mRight = ta.getDrawable(R.styleable.SeekBar_rightindicater);
        ta.recycle();

        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mLeftRect.set(0, 0, 60, 60);
        mRightRect.set(w - 60, 0, w, 60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPhase < 3) {
            return;
        }
        int y = mLeftRect.top + mLeftRect.height() / 2;
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(mLeftRect.left, y, mRightRect.right, y, mPaint);
        mLeft.setBounds(mLeftRect);
        mRight.setBounds(mRightRect);
        mLeft.draw(canvas);
        mRight.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDragging = checkIsCanDrag(event);
                if (isDragging) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                move(mTemp, x);
                break;
            default:
                break;
        }
        if (isDragging) {

            invalidate();
        }
        return isDragging;
    }

    private void move(Rect target, float x) {
        int width = target.width();
        int real = (int) (x - width / 2f);
        target.set(real, target.top, real + width, target.bottom);
    }

    private boolean checkIsCanDrag(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (mLeftRect.contains(x, y)) {
                mTemp = mLeftRect;
                return true;
            }
            if (mRightRect.contains(x, y)) {
                mTemp = mRightRect;
                return true;
            }
        }
        return false;
    }
}

package com.yushilei.commonapp.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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

    private List<Point> units = new ArrayList<>();

    private Indicator mLeftIndicator;
    private Indicator mRightIndicator;

    private boolean isDragging = false;

    private Indicator mTemp;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private boolean isAniPlaying = false;


    private OnSeekBarChangeListener mChangeListener;

    public void setChangeListener(OnSeekBarChangeListener changeListener) {
        mChangeListener = changeListener;
    }

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
        Drawable left = ta.getDrawable(R.styleable.SeekBar_leftindicater);
        Drawable right = ta.getDrawable(R.styleable.SeekBar_rightindicater);
        mLeftIndicator = new Indicator(left);
        mRightIndicator = new Indicator(right);
        ta.recycle();

        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(6);
        mPaint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < mPhase; i++) {
            units.add(new Point());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mPhase < 3) {
            return;
        }
        int paddingTop = getPaddingTop();
        int hw = h - paddingTop - getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int validWidth = w - paddingLeft - getPaddingRight();
        int middleY = h / 2;
        float per = (validWidth - hw) / (mPhase - 1f);
        for (int i = 0; i < mPhase; i++) {
            units.get(i).set((int) (hw / 2 + i * per), middleY);
        }

        Rect bound = new Rect(paddingLeft, paddingTop, hw, hw);
        mLeftIndicator.setBound(bound);
        mRightIndicator.setBound(bound);

        mLeftIndicator.setUnit(units.get(0));
        mRightIndicator.setUnit(units.get(units.size() - 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPhase < 3) {
            return;
        }
        int y = getHeight() / 2;
        //背景线
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(units.get(0).x, y, units.get(units.size() - 1).x, y, mPaint);
        //中间线
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(mLeftIndicator.mRect.left, y, mRightIndicator.mRect.right, y, mPaint);

        mLeftIndicator.mTag.draw(canvas);
        mRightIndicator.mTag.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAniPlaying) {
            return false;
        }
        float x = event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDragging = checkIsCanDrag(event);
                if (isDragging) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    float fitX = getFitX(mTemp, x);
                    mTemp.move(fitX);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isDragging) {
                    Point point = getAutoFitPoint(mTemp);
                    mTemp.autoFitWithAnimation(point);
                    onChangeCallBack();
                }
                isDragging = false;
                mTemp = null;
                break;
            default:
                break;
        }
        if (isDragging) {

            invalidate();
        }
        return isDragging;
    }

    private float getFitX(Indicator temp, float x) {
        float real;
        if (temp == mRightIndicator) {
            real = x > mLeftIndicator.match.x ? x : mLeftIndicator.match.x;
        } else {
            real = x < mRightIndicator.match.x ? x : mRightIndicator.match.x;
        }
        return real;
    }

    private void onChangeCallBack() {
        if (mChangeListener != null) {
            int min = units.indexOf(mLeftIndicator.match);
            int max = units.indexOf(mRightIndicator.match);
            mChangeListener.onChange(min, max);
        }
    }

    private Point getAutoFitPoint(Indicator temp) {
        int start;
        int end;
        if (temp == mRightIndicator) {
            start = units.indexOf(mLeftIndicator.match) + 1;
            end = units.size();
        } else {
            start = 0;
            end = units.indexOf(mRightIndicator.match);
        }
        List<Point> points = units.subList(start, end);
        return getFitPoint(points, temp.mRect);
    }

    public Point getFitPoint(List<Point> valid, Rect rect) {
        int x = rect.left + rect.width() / 2;
        Point temp = new Point();
        int offset = Integer.MAX_VALUE;
        for (int i = 0; i < valid.size(); i++) {
            Point point = valid.get(i);
            int abs = Math.abs(point.x - x);
            if (abs < offset) {
                offset = abs;
                temp = point;
            }
        }
        return temp;
    }

    private boolean checkIsCanDrag(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (mLeftIndicator.isUnderTouch(x, y)) {
                mTemp = mLeftIndicator;
                return true;
            }
            if (mRightIndicator.isUnderTouch(x, y)) {
                mTemp = mRightIndicator;
                return true;
            }
        }
        return false;
    }

    public void setMinAndMax(int min, int max) {
        if (max > min) {
            if (checkNumVaild(min) && checkNumVaild(max)) {
                Point minP = units.get(min);
                Point maxP = units.get(max);
                mLeftIndicator.setUnit(minP);
                mRightIndicator.setUnit(maxP);
                invalidate();
            }
        }
    }

    private boolean checkNumVaild(int num) {
        int realMax = units.size() - 1;
        return num >= 0 && num <= realMax;
    }

    public interface OnSeekBarChangeListener {
        void onChange(int min, int max);
    }

    private final class Indicator {
        Drawable mTag;
        Rect mRect = new Rect();
        Point match;
        private ObjectAnimator mAni;

        Indicator(Drawable tag) {
            mTag = tag;
        }

        void setUnit(Point p) {
            match = p;
            move(p.x);
        }

        void setBound(Rect rect) {
            mRect.set(rect);
            mTag.setBounds(mRect);
        }

        boolean isUnderTouch(int x, int y) {
            return mRect.contains(x, y);
        }

        int getX() {
            return mRect.left + mRect.width() / 2;
        }

        void autoFitWithAnimation(Point end) {
            match = end;
            if (mAni == null) {
                mAni = ObjectAnimator.ofFloat(new Object() {
                    public void setX(float x) {
                        move(x);
                        invalidate();
                    }
                }, "X", getX(), end.x);
                mAni.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        isAniPlaying = false;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAniPlaying = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        isAniPlaying = true;
                    }
                });
            }
            mAni.setFloatValues(getX(), end.x);
            mAni.setDuration(200);
            mAni.start();
        }

        void move(float x) {
            int width = mRect.width();
            int real = (int) (x - width / 2f);
            mRect.set(real, mRect.top, real + width, mRect.bottom);
            mTag.setBounds(mRect);
        }
    }
}

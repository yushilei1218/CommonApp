package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since on 2017/7/31.
 */

public class BannerIndicatorView extends View implements ViewPager.OnPageChangeListener, BannerView.OnIndicatorCountChangeListener {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mOffset;
    private int mNormalColor;
    private int mSelectedColor;
    private int mRadius;

    private int mIndicatorCount = 0;
    private int mCurSelectedIndex = 0;

    public BannerIndicatorView(Context context) {
        this(context, null);
    }

    public BannerIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerIndicatorView);
        mOffset = a.getInt(R.styleable.BannerIndicatorView_indicatorOffset, 5);
        mNormalColor = a.getColor(R.styleable.BannerIndicatorView_indicatorColorNormal, Color.BLACK);
        mSelectedColor = a.getColor(R.styleable.BannerIndicatorView_indicatorColorSelected, Color.WHITE);
        mRadius = a.getInt(R.styleable.BannerIndicatorView_indicatorRadius, 5);
        a.recycle();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("BannerIndicatorView", "onMeasure");
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int indicatorHeight = 2 * mRadius;
        int totalIndicatorWidth = 0;
        if (mIndicatorCount >= 2) {
            totalIndicatorWidth = mIndicatorCount * 2 * mRadius + (mIndicatorCount - 1) * mOffset;
        }
        int totalHeight = paddingBottom + paddingTop + indicatorHeight;
        int totalWidth = totalIndicatorWidth + paddingLeft + paddingRight;
        int newWSpec = MeasureSpec.makeMeasureSpec(totalWidth, MeasureSpec.EXACTLY);
        int newHSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        super.onMeasure(newWSpec, newHSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIndicatorCount >= 2) {
            for (int i = 0; i < mIndicatorCount; i++) {
                int cx = getPaddingLeft() + mRadius + i * (mOffset + 2 * mRadius);
                int cy = getPaddingTop() + mRadius;
                if (mCurSelectedIndex == i) {
                    mPaint.setColor(mSelectedColor);
                } else {
                    mPaint.setColor(mNormalColor);
                }
                canvas.drawCircle(cx, cy, mRadius, mPaint);
            }
        }
    }

    public void setIndicatorCount(int indicatorCount) {
        mIndicatorCount = indicatorCount;
        requestLayout();
    }

    /*ViewPager OnPageChangeListener回调*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurSelectedIndex = position % mIndicatorCount;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*BannerView OnIndicatorCountChangeListener回调*/
    @Override
    public void onIndicatorChange(int count) {
        setIndicatorCount(count);
    }
}

package com.yushilei.commonapp.common.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yushilei.commonapp.R;


/**
 * @auther by yushilei.
 * @time 2017/6/20-12:30
 * @desc
 */

public class PtrClockView extends View {

    private float mCurDegrees = 0.0f;

    private Bitmap clockBitmap;
    private Bitmap hourBitmap;
    private Bitmap minBitmap;

    private static final float mScanDegrees = 240f;
    private ObjectAnimator mLoadAni;
    private ObjectAnimator mDragDownAni;
    private ObjectAnimator mDragUpAni;

    private static final int mScanDuration = 300;

    public PtrClockView(Context context) {
        super(context, null);
    }

    public PtrClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        clockBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_clock);
        hourBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_clock_hour);
        minBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_clock_min);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = clockBitmap.getWidth();
        int height = clockBitmap.getHeight();
        int widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthSpec, heightSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(clockBitmap, 0, 0, null);
        drawRotateBitmap(canvas, hourBitmap, mCurDegrees / 12f);
        drawRotateBitmap(canvas, minBitmap, mCurDegrees);

    }

    private void drawRotateBitmap(Canvas canvas, Bitmap bitmap, float degrees) {
        canvas.save();
        canvas.translate(getWidth() / 2f, getHeight() / 2f);
        canvas.rotate(degrees);
        canvas.drawBitmap(bitmap, -getWidth() / 2f, -getHeight() / 2f, null);
        canvas.restore();
    }

    public void startLoadDragUpAni() {
        mCurDegrees = mScanDegrees;
        cancelAni();
        mDragUpAni = ObjectAnimator.ofFloat(this, "mCurDegrees", mCurDegrees, 0.0f);
        mDragUpAni.setDuration(mScanDuration);
        mDragUpAni.start();
    }

    public void startLoadDragDownAni() {
        mCurDegrees = 0.0f;
        cancelAni();
        mDragDownAni = ObjectAnimator.ofFloat(this, "mCurDegrees", mCurDegrees, mScanDegrees);
        mDragDownAni.setDuration(mScanDuration);
        mDragDownAni.start();
    }

    public void startLoadAni() {
        cancelAni();
        mLoadAni = ObjectAnimator.ofFloat(this, "mCurDegrees", mCurDegrees, mCurDegrees + 360f * 60 * 60);
        mLoadAni.setDuration(720 * 60 * 60);
        mLoadAni.setInterpolator(new LinearInterpolator());
        mLoadAni.setRepeatCount(ValueAnimator.INFINITE);
        mLoadAni.start();
    }

    public void setMCurDegrees(float mCurDegrees) {
        this.mCurDegrees = mCurDegrees;
        invalidate();
    }

    private void cancelAni() {
        if (mLoadAni != null)
            mLoadAni.cancel();
        if (mDragDownAni != null)
            mDragDownAni.cancel();
        if (mDragUpAni != null)
            mDragUpAni.cancel();
    }

    public void reset() {
        cancelAni();
        mCurDegrees = 0.0f;
        invalidate();
    }
}

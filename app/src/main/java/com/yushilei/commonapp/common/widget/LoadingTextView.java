package com.yushilei.commonapp.common.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since on 2017/7/13.
 */

public class LoadingTextView extends AppCompatTextView {

    private float Degree = 0f;

    private static final float mBitmapHeightRate = 0.4f;
    private Drawable drawable;

    private Rect mCacheRect = new Rect();
    private int textColor;

    public LoadingTextView(Context context) {
        this(context, null);
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        if (Build.VERSION.SDK_INT >= 21) {
//            drawable = (RotateDrawable) context.getResources().getDrawable(R.drawable.rotate_drawable, context.getTheme());
//        } else {
        drawable = (Drawable) context.getResources().getDrawable(R.mipmap.icon_login_progressbar);
//        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingTextView);
        textColor = a.getInt(R.styleable.LoadingTextView_atextColor,0);

        a.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置文字居中
        getPaint().setTextAlign(Paint.Align.CENTER);
        getPaint().setColor(textColor);
        String text = getText().toString();
        //绘制文字
        canvas.drawText(text, getWidth() / 2f, getHeight() / 2f - getTextOffset(), getPaint());
        //计算Drawable大小
        computeDrawableRect();
        //设置大小
        computeDrawableRect2();
        drawable.setBounds(mCacheRect);
        //画
        canvas.rotate(Degree, (mCacheRect.right - mCacheRect.left) / 2f + mCacheRect.left, getHeight() / 2f);
        drawable.draw(canvas);
    }

    public void setDegree(float degree) {
        this.Degree = degree;
        invalidate();
    }

    public void startAni() {

        ObjectAnimator ani = ObjectAnimator.ofFloat(this, "Degree", 0f, 360f);
        ani.setRepeatCount(-1);
        ani.setInterpolator(new LinearInterpolator());
        ani.setDuration(1000);
        ani.start();
    }

    private void computeDrawableRect2() {
        int top = (getHeight() - mCacheRect.height()) / 2;
        int x = (int) getLoadingImgX();
        int with = (int) (getHeight() * mBitmapHeightRate);
        mCacheRect.set(x, top, x + with, top + with);

    }

    private void computeDrawableRect() {
        int with = (int) (getHeight() * mBitmapHeightRate);
        mCacheRect.set(0, 0, with, with);
    }

    private float getLoadingImgX() {
        float textWidth = getPaint().measureText(getText().toString());
        float leftWidth = (getWidth() - textWidth) / 2f;
        return leftWidth - mCacheRect.width() - (getHeight() - mCacheRect.height()) / 2;
    }

    private float getTextOffset() {
        TextPaint paint = getPaint();
        Paint.FontMetricsInt metricsInt = paint.getFontMetricsInt();
        int offset = metricsInt.ascent + metricsInt.descent;
        return offset / 2f;
    }
}

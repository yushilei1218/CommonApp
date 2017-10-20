package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;


/**
 * @author shilei.yu
 * @since 2017/10/20
 */

public class NumAdaptiveTextView extends AppCompatTextView {
    private float textSize;

    public NumAdaptiveTextView(Context context) {
        super(context);
    }

    public NumAdaptiveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        textSize = getTextSize();
        setSingleLine();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            throw new RuntimeException("视图宽度必须固定");
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (TextUtils.isEmpty(getText())) {
            super.onDraw(canvas);
        } else {
            TextPaint paint = getPaint();
            int width = getWidth();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int textWidth = width - paddingLeft - paddingRight;

            float realTextWidth = paint.measureText((String) getText());
            while (realTextWidth + 20 > textWidth) {
                float textSize = paint.getTextSize();
                paint.setTextSize(textSize - 2f);
                realTextWidth = paint.measureText((String) getText());
            }

            super.onDraw(canvas);
        }
    }
}

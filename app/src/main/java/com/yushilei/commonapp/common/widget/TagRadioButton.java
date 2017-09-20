package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since 2017/9/20
 */

public class TagRadioButton extends AppCompatRadioButton {

    private boolean isShowTag = false;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TagRadioButton(Context context) {
        super(context);
        init();
    }

    public TagRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (isShowTag) {
            canvas.drawCircle(width * 0.8f, height * 0.2f, 10, mPaint);
        }

    }

    public void setShowTag(boolean showTag) {
        isShowTag = showTag;
        invalidate();
    }
}

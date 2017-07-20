package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author shilei.yu
 * @since on 2017/7/19.
 */

public class SwitchButton extends View {
    private RectF mBackGround = new RectF();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean isOff;

    public SwitchButton(Context context) {
        super(context);
        init(context);
    }


    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint.setColor(Color.parseColor("#000000"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(4);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int width = getWidth();
        int height = getHeight();
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        float rx = (height - paddingBottom - paddingTop) / 2f;
        mBackGround.set(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        canvas.drawRoundRect(mBackGround, rx, rx, paint);
        paint.setShadowLayer(5,2,2,Color.DKGRAY);
        float y = (height - paddingTop - paddingBottom) / 2f + paddingTop;
        canvas.drawCircle(paddingLeft + mBackGround.height() / 2f, y, mBackGround.height() / 2f, paint);
    }
}

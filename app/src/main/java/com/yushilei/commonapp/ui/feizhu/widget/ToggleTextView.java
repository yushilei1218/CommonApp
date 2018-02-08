package com.yushilei.commonapp.ui.feizhu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public class ToggleTextView extends android.support.v7.widget.AppCompatTextView{
    public ToggleTextView(Context context) {
        super(context);
    }

    public ToggleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToggleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}

package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.WindowManager;

/**
 * @author shilei.yu
 * @since on 2017/7/18.
 */

public class FixedHeightRecyclerView extends RecyclerView {
    private static final float rate = 1.0f;

    private Point outSize;

    public FixedHeightRecyclerView(Context context) {
        super(context);
        init(context);
    }


    public FixedHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int newHSpec = MeasureSpec.makeMeasureSpec(outSize.x, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, newHSpec);
    }
}

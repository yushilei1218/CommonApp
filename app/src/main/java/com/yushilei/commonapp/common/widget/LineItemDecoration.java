package com.yushilei.commonapp.common.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yushilei.commonapp.common.base.BaseApp;


/**
 * RecyclerView Item 分割线
 * <p>
 * 适用于垂直排版的RecyclerView 仅仅在Item之间画一条1px 高度的分割线。
 * 分割线颜色参见构造函数。
 *
 * @author shilei.yu
 * @since on 2017/8/16.
 */

public class LineItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public LineItemDecoration(@ColorRes int rid) {
        Resources resources = BaseApp.AppContext.getResources();
        int color = resources.getColor(rid);
        mPaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int bottom = child.getBottom();
            c.drawLine(left, bottom, right, bottom, mPaint);
        }
    }
}

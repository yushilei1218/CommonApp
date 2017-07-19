package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.WindowManager;

import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.MapView;
import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since on 2017/7/18.
 */

public class Map2View extends MapView {
    /**
     * x px宽度， y px高度
     */
    private Point outSize;

    public Map2View(Context context) {
        super(context);
        init(context);
    }

    public Map2View(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public Map2View(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public Map2View(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
        init(context);

    }

    private void init(Context context) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHSpec = MeasureSpec.makeMeasureSpec((int) (outSize.y * 3f / 4f), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHSpec);
    }
}

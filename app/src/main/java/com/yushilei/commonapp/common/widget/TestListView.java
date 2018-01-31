package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author shilei.yu
 * @since on 2018/1/30.
 */

public class TestListView extends ListView {
    private static final String TAG = "TestListTAG";

    public TestListView(Context context) {
        super(context);
    }

    public TestListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        log("onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        log("onLayout changed=" + changed);
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void layoutChildren() {
        log("layoutChildren");
        super.layoutChildren();
    }

    @Override
    protected void attachViewToParent(View child, int index, ViewGroup.LayoutParams params) {
        log("attachViewToParent index=" + index);
        super.attachViewToParent(child, index, params);
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }
}

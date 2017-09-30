package com.yushilei.commonapp.common.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since 2017/9/29
 */

public class InitLocationBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "InitLocationBehavior";

    public InitLocationBehavior() {
    }

    public InitLocationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.act_search_coordinator;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "parent=" + parent + " child=" + child);
        child.setTop(200);
        return true;
    }
}

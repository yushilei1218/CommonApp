package com.yushilei.commonapp.common.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since 2017/9/30
 */

public class DropLocationBehavior extends CoordinatorLayout.Behavior {
    public DropLocationBehavior() {
    }

    public DropLocationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.act_search_tab;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY() + dependency.getHeight());
        return true;
    }
}

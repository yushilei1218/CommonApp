package com.yushilei.commonapp.common.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since 2017/9/29
 */

public class InitLocationBehavior extends CoordinatorLayout.Behavior {
    public InitLocationBehavior() {
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.act_search_tab;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}

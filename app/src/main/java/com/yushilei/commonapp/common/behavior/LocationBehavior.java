package com.yushilei.commonapp.common.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.widget.FixedHeightRecyclerView;

/**
 * @author shilei.yu
 * @since on 2017/7/19.
 */

public class LocationBehavior extends CoordinatorLayout.Behavior<FixedHeightRecyclerView> {
    public LocationBehavior() {
    }

    public LocationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FixedHeightRecyclerView child, View dependency) {
        return dependency.getId() == R.id.map_v;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FixedHeightRecyclerView child, View dependency) {
        float y = dependency.getY();
        int height = dependency.getHeight();
        ViewCompat.setY(child, y + height);
        return true;
    }
}

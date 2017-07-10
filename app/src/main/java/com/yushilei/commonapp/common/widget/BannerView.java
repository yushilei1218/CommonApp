package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * 广告位轮换View
 *
 * @author shilei.yu
 * @see #onMeasure(int, int) 重写该方法BannerView 宽高依赖其第一个child
 * @since on 2017/7/10.
 */

public class BannerView extends ViewPager {
    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child = getChildAt(0);
        if (child != null) {
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    public class BannerPagerAdapter<T> extends PagerAdapter {
        List<T> data = new LinkedList<>();

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            T t = data.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
        }

    }
}

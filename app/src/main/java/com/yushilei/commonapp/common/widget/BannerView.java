package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.util.SetUtil;

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

    private Adapter adapter;
    private int touchSlop;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        adapter = new Adapter();
        setAdapter(adapter);

        ViewConfiguration con = ViewConfiguration.get(context);

        touchSlop = con.getScaledTouchSlop();
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
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

    public class Adapter extends PagerAdapter {
        List<BannerWrapper> data = new LinkedList<>();

        Pools.SimplePool<View> mViewPool = new Pools.SimplePool<>(5);

        public void addAll(List<BannerWrapper> data) {
            if (SetUtil.isEmpty(data)) {
                this.data.clear();
            } else {
                this.data.addAll(data);
            }
            notifyDataSetChanged();
        }

        @Override

        public int getCount() {
            if (SetUtil.isEmpty(data))
                return 0;
            if (data.size() == 1)
                return 1;
            return Integer.MAX_VALUE >> 2;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i("BannerView", "instantiateItem" + position);
            BannerWrapper wrapper = data.get(position % data.size());

            View itemView = mViewPool.acquire();
            if (itemView == null) {
                itemView = LayoutInflater.from(container.getContext())
                        .inflate(wrapper.getLayoutRes(), container, false);
                itemView.setTag(new BannerHolder(itemView));
            }
            BannerHolder holder = (BannerHolder) itemView.getTag();

            wrapper.bindView(container, holder, position);

            container.addView(itemView);


            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("BannerView", "destroyItem" + position);
            View view = (View) object;
            mViewPool.release(view);
            container.removeView(view);
        }
    }

    public class BannerHolder {
        private SparseArray<View> mViews = new SparseArray<>();

        public final View itemView;

        private BannerHolder(View itemView) {
            this.itemView = itemView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T findView(int rid) {
            View view = mViews.get(rid);
            if (view == null) {
                view = itemView.findViewById(rid);
                mViews.append(rid, view);
            }
            return (T) view;
        }
    }

    public static abstract class BannerWrapper<T> implements IBannerItem {
        public T bean;

        public BannerWrapper(T bean) {
            this.bean = bean;
        }
    }

    private interface IBannerItem {
        @LayoutRes
        int getLayoutRes();

        void bindView(ViewGroup container, BannerHolder holder, int pos);
    }
}
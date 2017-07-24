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
import android.view.View;
import android.view.ViewGroup;

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

public class BannerView extends ViewPager implements Runnable {
    private static final String TAG = "BannerView";

    private static final int SLEEP_TIME = 3000;
    /**
     * 是否已经附在window上
     */
    private boolean isAttachedToWindow = false;
    /**
     * 是否处于轮播状态
     */
    private boolean isLooping = false;

    private Adapter adapter;

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
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG,"onAttachedToWindow");
        isAttachedToWindow = true;
        stopLoop();
        if (adapter.isCanLoop()) {
            startLoop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i(TAG,"onDetachedFromWindow");
        stopLoop();
        isAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    /**
     * 开始轮播
     */
    public void startLoop() {
        if (!adapter.isCanLoop())
            return;
        if (isLooping) {
            stopLoop();
        }
        isLooping = true;
        postDelayed(this, SLEEP_TIME);
    }

    /**
     * 结束轮播
     */
    public void stopLoop() {
        isLooping = false;
        removeCallbacks(this);
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        if (child != null) {
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int newHSpec = MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, newHSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /*BannerView 实现Runnable*/
    @Override
    public void run() {
        int currentItem = getCurrentItem();
        setCurrentItem(currentItem + 1, true);
        postDelayed(this, SLEEP_TIME);
    }

    public class Adapter extends PagerAdapter {
        /*数据源*/
        private List<BannerWrapper> data = new LinkedList<>();
        /*ItemView 缓存*/
        private Pools.SimplePool<View> mViewPool = new Pools.SimplePool<>(5);

        /**
         * 添加轮播数据源 ，刷新BannerView，自动判断是否开启轮播
         *
         * @param data 数据源
         */
        public void addDataAndLoop(List<BannerWrapper> data) {
            stopLoop();

            if (SetUtil.isEmpty(data)) {
                this.data.clear();
            } else {
                this.data.addAll(data);
            }
            notifyDataSetChanged();

            if (isCanLoop()) {
                startLoop();
            }
        }

        /**
         * 结合BannerView 状态，和数据源 判定是否可以进行轮播
         *
         * @return true:可以轮播；false 不可轮播
         */
        @SuppressWarnings("RedundantIfStatement")
        private boolean isCanLoop() {
            if (!isAttachedToWindow) {
                return false;
            }
            if (SetUtil.isEmpty(data))
                return false;
            if (data.size() == 1)
                return false;
            return true;
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
            View view = (View) object;
            container.removeView(view);
            mViewPool.release(view);
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

    /**
     * BannerView  item 抽象类 ,需要创建新的包装类继承该抽象类，并且包装数据源
     *
     * @param <T> 数据源
     */
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
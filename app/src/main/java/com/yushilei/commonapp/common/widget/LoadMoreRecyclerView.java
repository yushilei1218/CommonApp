package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.BuildConfig;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;

/**
 * LoadMoreRecyclerView 用于提供上拉加载更多 负责维护Footer
 * 内部添加RecyclerView 滑动监听OnScrollListener 当向下滑动，并且数据源倒数第二项已完全展示时
 * 在RecyclerView  state IDLE 时展示 Footer Loading
 * <p>
 * 注意：LoadMoreRecyclerView使用的Adapter 必须为{@link MultiRecyclerAdapter}
 * 并且目前仅支持 {@link LinearLayoutManager},如需支持其他LayoutManager 请自行修改processScrollDown()方法
 *
 * @author shilei.yu
 * @see #loadFinish() 供外部调用，会变更Footer state为{@link FootSate#NORMAL} 移除Footer
 * @see #noMore()  *请在添加数据完成后调用*供外部调用，会变更Footer state为{@link FootSate#NO_MORE} 并添加Footer
 * @see #processScrollDown()  处理滑动回调的方法参见该方法
 * @since on 2017/7/10.
 */

public class LoadMoreRecyclerView extends RecyclerView {
    private static final String TAG = "LoadMoreRecyclerView";
    /*当前View 使用的MultiRecyclerAdapter*/
    private MultiRecyclerAdapter mAdapter;
    /**
     * 当前LoadMoreRecyclerView Footer
     * 如需更改Footer样子{@link FootWrapper#getLayoutRes()}
     * Footer维护的UI 方法参见 {@link FootWrapper#onBindViewHolder(BaseViewHolder, int)} }
     */
    private FootWrapper mFooter = new FootWrapper(new FootItem(FootSate.NORMAL));
    /**
     * 是否向下滑动
     */
    private boolean isScrollDown = false;
    /**
     * 加载更多 回调接口，如不设置，则默认不支持Footer
     */
    private LoadMoreListener loadMoreListener;
    /**
     * 是否需要loadMore功能，当loadMoreListener 不为空时才起作用
     * 使用场景：当触发下拉刷新时，loading过程中，不应该触发上拉刷新
     */
    private boolean canLoadMore = true;

    /**
     * @see #loadMoreListener 在监听不为空的时候才有效
     * 设置上拉时是否可以触发 加载更多
     */
    public void setCanLoadMore(boolean needLoadMore) {
        this.canLoadMore = needLoadMore;
    }

    /**
     * 上拉操作是否可以触发加载更多
     *
     * @return true 可以；false 不可以
     */
    public boolean isCanLoadMore() {
        return loadMoreListener != null && canLoadMore;
    }

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (isScrollDown && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    processScrollDown();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                isScrollDown = dy >= 0;
            }
        });
    }

    /**
     * 当向下滑动时-RecyclerView state变成IDLE时调用该方法
     * 判定是否要进行加载更多
     */
    private void processScrollDown() {
        if (loadMoreListener == null) {
            return;
        }
        if (!canLoadMore) {
            return;
        }
        if (mFooter.bean.curState != FootSate.NORMAL) {
            return;
        }
        if (mAdapter == null) {
            return;
        }
        boolean match = getLayoutManager() instanceof LinearLayoutManager;
        if (!match) {
            throw new IllegalArgumentException("LoadMoreRecyclerView 仅支持LinearLayoutManager，如需支持其他，请自行修改源码！");
        }
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
        if (manager == null) {
            return;
        }
        int count = manager.getChildCount();
        if (count <= 0) {
            return;
        }
        int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
        int totalItemCount = manager.getItemCount();
//        if (BuildConfig.DEBUG)
//            Log.i(TAG, "processScrollDown lastVisibleItem=" + lastVisibleItem + " totalItemCount=" + totalItemCount);
//
        if (lastVisibleItem == (totalItemCount - 1)) {
            //加载更多功能的代码
            mFooter.bean.curState = FootSate.LOADING;
            mAdapter.addLast(mFooter);
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof MultiRecyclerAdapter) {
            mAdapter = (MultiRecyclerAdapter) adapter;
            super.setAdapter(adapter);
        } else {
            throw new IllegalArgumentException("LoadMoreRecyclerView 需要使用MultiRecyclerAdapter！");
        }
    }

    /**
     * 加载完毕，Footer state置成FootSate.NORMAL
     * 并移除Footer
     *
     * @see #mFooter
     */
    public void loadFinish() {
        mFooter.bean.curState = FootSate.NORMAL;
        mAdapter.remove(mFooter);
    }

    /**
     * 加载完毕且无更多数据，Footer state置成FootSate.NO_MORE
     * 并自动将Footer加到队尾
     * ***注意该方法需要在添加数据完成后调用***！！！
     *
     * @see #mFooter
     */
    public void noMore() {
        mFooter.bean.curState = FootSate.NO_MORE;
        mAdapter.remove(mFooter);
        mAdapter.addLast(mFooter);
    }

    /**
     * 设置 加载更多回调接口，如不设置则默认不支持Footer
     */
    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }

    /*
            数据包装部分
             */
    private class FootWrapper extends ItemWrapper<FootItem> {

        private FootWrapper(FootItem bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.footer_recycler;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            View loadingLayout = holder.findView(R.id.footer_loading_layout);
            View noMoreLayout = holder.findView(R.id.footer_no_more_layout);
            switch (bean.curState) {
                case LOADING:
                    loadingLayout.setVisibility(VISIBLE);
                    noMoreLayout.setVisibility(GONE);
                    break;
                case NO_MORE:
                    loadingLayout.setVisibility(GONE);
                    noMoreLayout.setVisibility(VISIBLE);
                    break;
                case NORMAL:
                    loadingLayout.setVisibility(GONE);
                    noMoreLayout.setVisibility(GONE);
                    break;
            }
        }
    }

    private class FootItem {
        private FootItem(FootSate curState) {
            this.curState = curState;
        }

        public FootSate curState;
    }

    private enum FootSate {
        LOADING,
        NO_MORE,
        NORMAL
    }
}

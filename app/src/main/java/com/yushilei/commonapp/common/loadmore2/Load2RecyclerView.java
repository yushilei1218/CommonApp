package com.yushilei.commonapp.common.loadmore2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;


/**
 * @author shilei.yu
 * @since on 2017/10/14.
 */

public class Load2RecyclerView extends RecyclerView {

    private FootAdapter mAdapter;
    private FootItem mFooter = new FootItem(FootSate.NORMAL);
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

    public Load2RecyclerView(Context context) {
        super(context);
    }

    public Load2RecyclerView(Context context, @Nullable AttributeSet attrs) {
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

    private void processScrollDown() {
        if (loadMoreListener == null) {
            return;
        }
        if (!canLoadMore) {
            return;
        }
        if (mFooter.curState != FootSate.NORMAL) {
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

        if (lastVisibleItem == (totalItemCount - 1)) {
            //加载更多功能的代码
            mFooter.curState = FootSate.LOADING;
            mAdapter.addFoot(mFooter);
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = new FootAdapter((MultiHolderAdapter) adapter);
        super.setAdapter(mAdapter);
    }

    public void loading() {
        mFooter.curState = FootSate.LOADING;
        mAdapter.addFoot(mFooter);
    }

    /**
     * 加载完毕，Footer state置成FootSate.NORMAL
     * 并移除Footer
     *
     * @see #mFooter
     */
    public void loadFinish() {
        mFooter.curState = FootSate.NORMAL;
        mAdapter.removeFoot();
    }

    /**
     * 加载完毕且无更多数据，Footer state置成FootSate.NO_MORE
     * 并自动将Footer加到队尾
     * ***注意该方法需要在添加数据完成后调用***！！！
     *
     * @see #mFooter
     */
    public void noMore() {
        mFooter.curState = FootSate.NO_MORE;
        mAdapter.addFoot(mFooter);
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}

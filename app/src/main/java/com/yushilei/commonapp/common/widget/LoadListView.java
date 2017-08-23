package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since 2017/8/23
 */

public class LoadListView extends ListView implements AbsListView.OnScrollListener {
    private FootStateEnum mStateEnum = FootStateEnum.FINISH;
    private boolean isCanLoadMore = true;
    private OnLoadMoreListener mLoadMoreListener;

    private View mFootView;
    private TextView mLoadTipTv;
    private ProgressBar mProgressBar;

    private int mLastItem = 0;
    private int mTotalItemCount = 0;

    public LoadListView(Context context) {
        this(context, null);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnScrollListener(this);
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        setSelector(colorDrawable);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mLoadMoreListener == null)
            return;
        if (!isCanLoadMore)
            return;
        if (mFootView == null)
            initFoot();
        if (scrollState == SCROLL_STATE_IDLE) {
            if (mTotalItemCount > 0 && mStateEnum == FootStateEnum.FINISH && mLastItem == mTotalItemCount) {
                changeFootState(FootStateEnum.LOADING);
                mLoadMoreListener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastItem = firstVisibleItem + visibleItemCount;
        mTotalItemCount = totalItemCount;
    }

    private void initFoot() {
        mFootView = LayoutInflater.from(getContext()).inflate(R.layout.lv_foot_load_more, null);
        mProgressBar = (ProgressBar) mFootView.findViewById(R.id.loadingBar);
        mLoadTipTv = (TextView) mFootView.findViewById(R.id.loadMoreTv);
    }


    public void loadFinish() {
        changeFootState(FootStateEnum.FINISH);
    }

    public void noMore() {
        changeFootState(FootStateEnum.NO_MORE);
    }

    public void setCanLoadMore(boolean isCanLoadMore) {
        this.isCanLoadMore = isCanLoadMore;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    private void changeFootState(FootStateEnum stateEnum) {
        if (mFootView == null)
            return;
        mStateEnum = stateEnum;

        switch (stateEnum) {
            case NO_MORE:
                mProgressBar.setVisibility(GONE);
                mLoadTipTv.setText("已经到底啦～");
                addFooterView(mFootView);
                break;
            case LOADING:
                mProgressBar.setVisibility(VISIBLE);
                mLoadTipTv.setText("正在加载...");
                addFooterView(mFootView);
                break;
            case FINISH:
                removeFooterView(mFootView);
                break;
        }
    }

    public enum FootStateEnum {
        NO_MORE,
        LOADING,
        FINISH
    }

    public interface OnLoadMoreListener {
        void onLoadingMore();
    }
}

package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yushilei.commonapp.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author shilei.yu
 * @since on 2017/7/11.
 */

public class PtrCustomHeader extends FrameLayout implements PtrUIHandler {

    private TextView mHeaderTv;
    private PtrClockView mClockV;

    public PtrCustomHeader(Context context) {
        this(context, null);
    }

    public PtrCustomHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        View header = LayoutInflater.from(context).inflate(R.layout.ptr_header, this, false);

        addView(header);
        mClockV = (PtrClockView) header.findViewById(R.id.ptr_header_clock);
        mHeaderTv = (TextView) header.findViewById(R.id.ptr_header_tv);
    }

    //————————Ptr滑动回调——————
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mClockV.reset();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mClockV.startLoadAni();
        mHeaderTv.setText("处理中...");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mClockV.reset();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
                mClockV.startLoadDragUpAni();
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
                mClockV.startLoadDragDownAni();
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mHeaderTv.setText("释放刷新");
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        if (frame.isPullToRefresh()) {
            mHeaderTv.setText("处理中...");
        } else {
            mHeaderTv.setText("下拉刷新");
        }
    }
}

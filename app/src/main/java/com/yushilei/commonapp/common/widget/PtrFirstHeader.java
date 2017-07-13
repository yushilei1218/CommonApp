package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yushilei.commonapp.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author shilei.yu
 * @since on 2017/7/13.
 */

public class PtrFirstHeader extends FrameLayout implements PtrUIHandler {
    private AnimationDrawable mAniDrawable;

    public PtrFirstHeader(@NonNull Context context) {
        this(context, null);
    }

    public PtrFirstHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        View header = LayoutInflater.from(context).inflate(R.layout.ptr_header_first, this, false);
        addView(header);
        ImageView aniImg = (ImageView) header.findViewById(R.id.ptr_header_ani_img);
        mAniDrawable = ((AnimationDrawable) aniImg.getDrawable());
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mAniDrawable.stop();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mAniDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mAniDrawable.stop();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}

package com.yushilei.commonapp.common.mvp;

import com.yushilei.commonapp.common.retrofit.CallPool;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public abstract class BasePresenter<View extends IBaseView> implements IBasePresenter {
    protected View mView;
    protected int mTaskId;

    protected boolean isDestroyed = false;

    public BasePresenter(View view) {
        this.mView = view;
        mTaskId = view.hashCode();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
        isDestroyed = true;
        removeTask();
        if (mView != null)
            mView = null;
    }

    @Override
    public void removeTask() {
        CallPool.cancelCall(mTaskId);
    }
}

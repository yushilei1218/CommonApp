package com.yushilei.commonapp.common.mvp;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public abstract class BasePresenter<View extends IBaseView> implements IBasePresenter {
    protected View mView;
    protected int mTaskId;

    public BasePresenter(View view) {
        this.mView = view;
        mTaskId = view.hashCode();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
        removeTask();
        mView = null;
    }

    @Override
    public void removeTask() {

    }
}

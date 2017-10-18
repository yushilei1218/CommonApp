package com.yushilei.commonapp.common.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yushilei.commonapp.common.base.BaseFragment;

/**
 * @author shilei.yu
 * @since on 2017/7/31.
 */

public abstract class MvpBaseFragment<P extends IBasePresenter> extends BaseFragment {

    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    protected abstract P getPresenter();
}

package com.yushilei.commonapp.common.mvp;

import com.yushilei.commonapp.common.base.BaseActivity;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public abstract class MvpBaseActivity<P extends IBasePresenter> extends BaseActivity {
    protected P presenter;

    public abstract P getPresenter();

    @Override
    protected void onDestroy() {
        if (presenter != null)
            presenter.onDestroy();
        super.onDestroy();
    }
}

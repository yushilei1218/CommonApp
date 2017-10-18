package com.yushilei.commonapp.common.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yushilei.commonapp.common.base.BaseActivity;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public abstract class MvpBaseActivity<P extends IBasePresenter> extends BaseActivity {
    protected P presenter;

    public abstract P getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = getPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }
}

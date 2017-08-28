package com.yushilei.commonapp.ui.filter;

import android.view.View;

import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;

/**
 * @author shilei.yu
 * @since 2017/8/28
 */

public interface FilterContract {
    interface IPresenter extends IBasePresenter {
    }

    interface IView extends IBaseView {

        void initFilterLayout(View target);
    }

    interface IModel {

    }
}

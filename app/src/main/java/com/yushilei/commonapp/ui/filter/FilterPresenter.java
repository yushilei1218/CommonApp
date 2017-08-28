package com.yushilei.commonapp.ui.filter;

import com.yushilei.commonapp.common.mvp.BasePresenter;

/**
 * @author shilei.yu
 * @since 2017/8/28
 */

public class FilterPresenter extends BasePresenter<FilterContract.IView> implements FilterContract.IPresenter {
    private FilterContract.IModel mModel;

    public FilterPresenter(FilterContract.IView view, FilterContract.IModel model) {
        super(view);
        mModel = model;
    }
}

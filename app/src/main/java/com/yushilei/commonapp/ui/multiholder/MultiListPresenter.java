package com.yushilei.commonapp.ui.multiholder;

import com.yushilei.commonapp.common.mvp.BasePresenter;

/**
 * @author shilei.yu
 * @since on 2017/8/14.
 */

public class MultiListPresenter extends BasePresenter<MultiListContract.IView> implements MultiListContract.Presenter {
    public MultiListPresenter(MultiListContract.IView view) {
        super(view);
    }

    @Override
    public void onBeanNameClick(Bean bean) {
        mView.onBeanNameClick(bean);
    }

    @Override
    public void onBeanAgeClick(Bean bean) {
        mView.onBeanAgeClick(bean);
    }

    @Override
    public void onBeanItemLongClick(Bean bean) {
        mView.onBeanItemLongClick(bean);
    }

    @Override
    public void onBookItemClick(Book book) {
        mView.onBookItemClick(book);
    }
}

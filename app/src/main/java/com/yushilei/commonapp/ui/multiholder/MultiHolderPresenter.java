package com.yushilei.commonapp.ui.multiholder;

import android.content.Intent;

import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.ui.ptr.PtrZpActivity;

/**
 * @auther by yushilei.
 * @time 2017/8/13-15:37
 * @desc
 */

public class MultiHolderPresenter extends BasePresenter<MultiHolderContract.IView> implements MultiHolderContract.Presenter {


    public MultiHolderPresenter(MultiHolderContract.IView view) {
        super(view);
    }

    @Override
    public void onRemoveBeanClick(Bean bean) {
        mView.onRemoveBean(bean);
    }

    @Override
    public void onBeanNameClick(Bean bean) {
        mView.showToast("Bean ：" + bean.name);
    }

    @Override
    public void onBeanLongClick(Bean bean) {
        mView.onBeanLongClick(bean);
    }

    @Override
    public void onBookImgClick(Book book) {

        mView.showToast("书名：" + book.name);
        mView.startActivity(new Intent(mView.getActivityContext(), PtrZpActivity.class));
    }
}

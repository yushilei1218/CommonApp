package com.yushilei.commonapp.ui.multiholder;

import android.content.Intent;

import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;
import com.yushilei.commonapp.ui.ptr.PtrZpActivity;

/**
 * @auther by yushilei.
 * @time 2017/8/13-15:37
 * @desc
 */

public class MultiHolderPresenter extends BasePresenter<MultiHolderContact.IView> implements MultiHolderContact.Presenter {


    public MultiHolderPresenter(MultiHolderContact.IView view) {
        super(view);
    }

    @Override
    public void onRemoveBeanClick(MultiHolderActivity.Bean bean) {
        mView.onRemoveBean(bean);
    }

    @Override
    public void onBeanNameClick(MultiHolderActivity.Bean bean) {
        mView.showToast("Bean ：" + bean.name);
    }

    @Override
    public void onBookImgClick(MultiHolderActivity.Book book) {

        mView.showToast("书名：" + book.name);
        mView.startActivity(new Intent(mView.getActivityContext(), PtrZpActivity.class));
    }
}

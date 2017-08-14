package com.yushilei.commonapp.ui.multiholder;

import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;

/**
 * @auther by yushilei.
 * @time 2017/8/13-15:35
 * @desc
 */

public class MultiHolderContract {
    public interface Presenter extends IBasePresenter {
        void onRemoveBeanClick(Bean bean);

        void onBeanNameClick(Bean bean);

        void onBeanLongClick(Bean bean);

        void onBookImgClick(Book book);

    }

    public interface IView extends IBaseView {
        void onRemoveBean(Bean bean);

        void onBeanLongClick(Bean bean);
    }
}

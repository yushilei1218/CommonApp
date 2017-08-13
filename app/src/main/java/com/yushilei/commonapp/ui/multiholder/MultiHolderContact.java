package com.yushilei.commonapp.ui.multiholder;

import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;

/**
 * @auther by yushilei.
 * @time 2017/8/13-15:35
 * @desc
 */

public class MultiHolderContact {
    public interface Presenter extends IBasePresenter {
        void onRemoveBeanClick(MultiHolderActivity.Bean bean);

        void onBeanNameClick(MultiHolderActivity.Bean bean);

        void onBookImgClick(MultiHolderActivity.Book book);
    }

    public interface IView extends IBaseView {
        void onRemoveBean(MultiHolderActivity.Bean bean);
    }
}

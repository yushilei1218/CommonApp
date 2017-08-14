package com.yushilei.commonapp.ui.multiholder;

import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;

/**
 * @author shilei.yu
 * @since on 2017/8/14.
 */

public class MultiListContract {
    public interface Presenter extends IBasePresenter {
        void onBeanNameClick(Bean bean);

        void onBeanAgeClick(Bean bean);

        void onBeanItemLongClick(Bean bean);

        void onBookItemClick(Book book);

    }

    public interface IView extends IBaseView {
        void onBeanNameClick(Bean bean);

        void onBeanAgeClick(Bean bean);

        void onBeanItemLongClick(Bean bean);

        void onBookItemClick(Book book);
    }
}

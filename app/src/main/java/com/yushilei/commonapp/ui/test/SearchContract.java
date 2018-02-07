package com.yushilei.commonapp.ui.test;

import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;
import com.yushilei.commonapp.ui.test.bean.ResourceBean;
import com.yushilei.commonapp.ui.test.bean.StatusBean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/7.
 */

public interface SearchContract {
    interface IPresenter extends IBasePresenter {

        void onFilterResourceClick();

        void onFilterStatusClick();

        void onFilterResourceClicked(ResourceBean bean);

        void onFilterStatusClicked(StatusBean bean);

    }

    interface IView extends IBaseView {
        void showFilterResourceView(List<ResourceBean> data);

        void showFilterStatusView(List<StatusBean> data);

        void showFilterResourceText(ResourceBean bean);

        void showFilterStatusText(StatusBean bean);
    }

    interface IModel {
        List<ResourceBean> getFilterResources();

        List<StatusBean> getFilterStatus();

        ResourceBean getSelectResourceBean();

        StatusBean getSelectStatusBean();

        void onResourceSelected(ResourceBean bean);

        void onStatusSelected(StatusBean bean);
    }
}

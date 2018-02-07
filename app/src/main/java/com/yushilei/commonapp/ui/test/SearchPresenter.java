package com.yushilei.commonapp.ui.test;

import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.ui.test.bean.ResourceBean;
import com.yushilei.commonapp.ui.test.bean.StatusBean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/7.
 */

public class SearchPresenter extends BasePresenter<SearchContract.IView> implements SearchContract.IPresenter {
    private SearchContract.IModel mModel;

    public SearchPresenter(SearchContract.IView view) {
        super(view);
        mModel = new SearchModel();
    }

    @Override
    public void onStart() {
        StatusBean temp = mModel.getSelectStatusBean();
        mView.showFilterStatusText(temp);
        ResourceBean temp1 = mModel.getSelectResourceBean();
        mView.showFilterResourceText(temp1);
    }

    @Override
    public void onFilterResourceClick() {
        List<ResourceBean> data = mModel.getFilterResources();
        mView.showFilterResourceView(data);
    }

    @Override
    public void onFilterStatusClick() {
        List<StatusBean> data = mModel.getFilterStatus();
        mView.showFilterStatusView(data);
    }

    @Override
    public void onFilterResourceClicked(ResourceBean bean) {
        mModel.onResourceSelected(bean);
        ResourceBean temp = mModel.getSelectResourceBean();
        mView.showFilterResourceText(temp);
    }

    @Override
    public void onFilterStatusClicked(StatusBean bean) {
        mModel.onStatusSelected(bean);
        StatusBean temp = mModel.getSelectStatusBean();
        mView.showFilterStatusText(temp);
    }
}

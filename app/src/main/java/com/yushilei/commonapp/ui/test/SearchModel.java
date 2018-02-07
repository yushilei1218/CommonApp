package com.yushilei.commonapp.ui.test;

import com.yushilei.commonapp.ui.test.bean.ResourceBean;
import com.yushilei.commonapp.ui.test.bean.StatusBean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/7.
 */

public class SearchModel implements SearchContract.IModel {
    private final List<ResourceBean> mResources = ResourceBean.get();
    private final List<StatusBean> mStatus = StatusBean.get();

    @Override
    public List<ResourceBean> getFilterResources() {
        return mResources;
    }

    @Override
    public List<StatusBean> getFilterStatus() {
        return mStatus;
    }

    @Override
    public ResourceBean getSelectResourceBean() {
        return ResourceBean.getBean();
    }

    @Override
    public StatusBean getSelectStatusBean() {
        return StatusBean.getBean();
    }

    @Override
    public void onResourceSelected(ResourceBean bean) {
        bean.setSelected();
    }

    @Override
    public void onStatusSelected(StatusBean bean) {
        bean.setSelected();
    }
}

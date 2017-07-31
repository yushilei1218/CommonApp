package com.yushilei.commonapp.common.mvp;

import android.view.View;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public interface IOperateView {
    /**
     * 初始化统一操作布局
     */
    void initOperateView();

    /**
     * 展示基础loading
     */
    void showLoading();

    /**
     * 隐藏基础loading
     */
    void hideLoading();

    /**
     * 展示loading Dialog
     */
    void showLoadingDialog();

    /**
     * 隐藏loading Dialog
     */
    void hideLoadingDialog();

    /*设置错误页面  带按钮和不带按钮设置*/
    void onError(int imageId, String msg, String btnText, View.OnClickListener onClickListener);
}

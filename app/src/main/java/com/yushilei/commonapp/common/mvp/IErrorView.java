package com.yushilei.commonapp.common.mvp;

import android.view.View;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public interface IErrorView {

    void initErrorView();

    void showLoading();

    void hideLoading();

    /*设置错误页面  带按钮和不带按钮设置*/
    void onError(int imageId, String msg, String btnText, View.OnClickListener onClickListener);
}

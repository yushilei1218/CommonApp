package com.yushilei.commonapp.ui.mvp.bean;

import com.yushilei.commonapp.common.mvp.IBaseView;

/**
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public enum LoadMode {
    PTR_LOAD,
    COM_LOAD,
    DIALOG_LOAD,
    LOAD_MORE_FINISH,
    LOAD_MODE_NO_MORE;

    public static void showLoading(LoadMode mode, IBaseView view) {
        switch (mode) {
            case PTR_LOAD:
                break;
            case COM_LOAD:
                view.showLoading();
                break;
            case DIALOG_LOAD:
                view.showLoadingDialog();
                break;
            default:
                break;
        }
    }

    public static void hideLoading(LoadMode mode, IBaseView view) {
        switch (mode) {
            case PTR_LOAD:
                break;
            case COM_LOAD:
                view.hideLoading();
                break;
            case DIALOG_LOAD:
                view.hideLoadingDialog();
                break;
            default:
                break;
        }
    }
}

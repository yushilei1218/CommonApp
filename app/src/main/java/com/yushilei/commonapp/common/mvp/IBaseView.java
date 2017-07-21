package com.yushilei.commonapp.common.mvp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public interface IBaseView extends IErrorView, View.OnClickListener {

    Context getActivityContext();

    void showToast(String msg);

    void initView();

    void initData();

    void showDialog(AlertDialog dialog);

    void finish();

    String getTAG();

    <T extends View> T findView(int rid);

}

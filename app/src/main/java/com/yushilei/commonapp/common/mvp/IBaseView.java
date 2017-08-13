package com.yushilei.commonapp.common.mvp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public interface IBaseView extends IOperateView, View.OnClickListener {

    Activity getActivityContext();

    void initView();

    void initData();

    void showToast(String msg);

    void showDialog(AlertDialog dialog);

    void finish();

    String getTAG();

    <T extends View> T findView(int rid);

    void startActivity(Intent intent);

    void startActivityForResult(Intent intent, int requestCode);
}

package com.yushilei.commonapp.ui.permission;


import android.Manifest;
import android.view.View;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

import java.util.List;


public class PermissionActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_permission;
    }

    @Override
    public void initView() {
        setOnClick(R.id.permission);
    }

    @Override
    public void onClick(View v) {
        AndPermission.with(this).permission(Manifest.permission.CAMERA)
                .requestCode(200)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, List<String> grantedPermissions) {
                        // Successfully.
                        if (requestCode == 200) {
                            showToast("onSucceed");
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> deniedPermissions) {
                        // Failure.
                        if (requestCode == 200) {
                            showToast("onFailed");
                        }
                    }
                }).start();
    }
}

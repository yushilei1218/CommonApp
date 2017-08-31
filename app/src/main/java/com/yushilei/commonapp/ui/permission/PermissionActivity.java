package com.yushilei.commonapp.ui.permission;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

import java.util.List;


public class PermissionActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_permission);
        findViewById(R.id.permission).setOnClickListener(this);
        findViewById(R.id.dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.permission:
                requestPermission();
                break;
            case R.id.dialog:
                requestPermissionByDialog();
                break;
        }


    }

    private void requestPermissionByDialog() {
        String[] items = new String[]{"图库", "相机", "取消"};
        new AlertDialog.Builder(this)
                .setTitle("选择")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://图库
                                selectPicFromGallery();
                                break;
                            case 1://相机
                                selectPicFromCamera();
                                break;
                            case 2://取消
                                dialog.dismiss();
                                break;

                        }
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void requestPermission() {
        AndPermission.with(this).permission(Manifest.permission.CAMERA)
                .requestCode(200)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, List<String> grantedPermissions) {
                        // Successfully.
                        if (requestCode == 200) {
                            Toast.makeText(PermissionActivity.this, "onSucceed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> deniedPermissions) {
                        // Failure.
                        if (requestCode == 200) {
                            Toast.makeText(PermissionActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
    }


    private void selectPicFromCamera() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(new String[]{
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        Log.d("Permission","onSucceed "+requestCode);
                        if (requestCode == 200) {
                            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera, 111);
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Log.d("Permission","onFailed "+requestCode);
                        Toast.makeText(PermissionActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
                    }
                }).start();
    }

    private void selectPicFromGallery() {

    }
}

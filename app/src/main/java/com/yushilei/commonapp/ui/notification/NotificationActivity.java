package com.yushilei.commonapp.ui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class NotificationActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView() {
        setOnClick(R.id.notification_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification_btn:
                sendNotification();
                break;
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder.setContentTitle("测试title")
                .setSmallIcon(R.mipmap.ic_clock)
                .setContentInfo("content 内容")
                .setContentText("content text")
                .setWhen(System.currentTimeMillis())
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}

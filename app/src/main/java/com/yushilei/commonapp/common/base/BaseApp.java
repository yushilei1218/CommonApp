package com.yushilei.commonapp.common.base;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

/**
 * App基类
 */

public class BaseApp extends Application {
    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = this;

        Fresco.initialize(this);
        //友盟
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        logChannel();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

    }

    private void logChannel() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            String value = appInfo.metaData.getString("UMENG_CHANNEL");
            Log.i("BaseApp", "UMENG_CHANNEL=" + value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}

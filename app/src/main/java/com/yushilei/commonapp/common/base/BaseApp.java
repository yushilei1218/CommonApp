package com.yushilei.commonapp.common.base;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mcxiaoke.packer.helper.PackerNg;
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

        logChannel();

        String channel = PackerNg.getChannel(this);
        if (TextUtils.isEmpty(channel))
            channel = "Zhaopin";

        Toast.makeText(this, "PackerNg channel=" + channel, Toast.LENGTH_SHORT).show();
        //友盟

        MobclickAgent.UMAnalyticsConfig config =
                new MobclickAgent.UMAnalyticsConfig(this, "5987dba41061d2650d0017ba", channel);
        MobclickAgent.startWithConfigure(config);

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

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

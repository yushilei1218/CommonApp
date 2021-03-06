package com.yushilei.commonapp.common.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.mcxiaoke.packer.helper.PackerNg;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.appfram.navigator.WXNavigatorModule;
import com.taobao.weex.common.WXException;
import com.umeng.analytics.MobclickAgent;
import com.yushilei.commonapp.common.bean.db.DaoMaster;
import com.yushilei.commonapp.common.bean.db.DaoSession;
import com.yushilei.commonapp.ui.weex.adapter.FrescoImageAdapter;
import com.yushilei.commonapp.ui.weex.adapter.OkHttpAdapter;
import com.yushilei.commonapp.ui.weex.adapter.UserTrackAdapter;
import com.yushilei.commonapp.ui.weex.module.EventModule;
import com.yushilei.commonapp.ui.weex.module.LocationModule;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * App基类
 */

public class BaseApp extends MultiDexApplication {
    public static BaseApp AppContext;

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = this;

        Logger.addLogAdapter(new LogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }

            @Override
            public void log(int priority, String tag, String message) {

            }
        });

        InitConfig con = new InitConfig.Builder()
                .setImgAdapter(new FrescoImageAdapter())
//                .setStorageAdapter()
//                .setURIAdapter()
                .setUtAdapter(new UserTrackAdapter())
                .setHttpAdapter(new OkHttpAdapter())
                .build();
        WXSDKEngine.initialize(this, con);

        try {
            WXSDKEngine.registerModule(LocationModule.class.getSimpleName(), LocationModule.class);
            WXSDKEngine.registerModule("ZPFEAPI", EventModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }

        Fresco.initialize(this);

        logChannel();

        String channel = PackerNg.getChannel(this);
        if (TextUtils.isEmpty(channel)) {
            channel = "Zhaopin";
        }

        Toast.makeText(this, "PackerNg channel=" + channel, Toast.LENGTH_SHORT).show();
        //友盟

        MobclickAgent.UMAnalyticsConfig config =
                new MobclickAgent.UMAnalyticsConfig(this, "5987dba41061d2650d0017ba", channel);
        MobclickAgent.startWithConfigure(config);

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                File root = Environment.getExternalStorageDirectory();
                File log = new File(root, "log.txt");
                FileWriter fos = null;

                try {
                    fos = new FileWriter(log);
                    fos.write(e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }
        });

    }

    public DaoSession getDaoSession() {
        return daoSession;
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

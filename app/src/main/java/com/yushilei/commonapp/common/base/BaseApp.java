package com.yushilei.commonapp.common.base;

import android.app.Application;
import android.content.Context;

/**
 * App基类
 */

public class BaseApp extends Application {
    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = this;

    }
}

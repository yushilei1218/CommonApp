package com.yushilei.commonapp.common.file;

import android.os.Environment;

import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.bean.base.IFocus;

import java.io.File;

/**
 * @author shilei.yu
 * @since 2017/12/1
 */

public class FileUtil {
    private FileUtil() {
    }

    private static final String ROOT = "com.yushilei.commonapp";
    private static final String JS_DIR = "js";

    public static File getRootDir() {
        File dir;
        if (Environment.isExternalStorageEmulated()) {
            dir = new File(Environment.getExternalStorageDirectory(), ROOT);
        } else {
            dir = new File(BaseApp.AppContext.getCacheDir(), ROOT);
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getJSCacheDir() {
        File dir = new File(getRootDir(), JS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}

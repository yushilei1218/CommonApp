package com.shileiyu.imageloader.util;

import android.os.Looper;

import java.io.File;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class Util {
    public static boolean isValidFile(File file) {
        if (file == null) {
            return false;
        }
        return file.exists() && file.length() > 0;
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}

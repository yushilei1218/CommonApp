package com.shileiyu.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;


/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class BitmapTranfer {
    public static Bitmap tranfer(File file, BitmapFactory.Options options) {
        if (Util.isValidFile(file)) {
            return BitmapFactory.decodeFile(file.getName(), options);
        }
        return null;
    }
}

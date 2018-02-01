package com.shileiyu.imageloader.cache;

import android.graphics.Bitmap;

import java.io.File;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class DiskCache<T extends File> implements IBitmapCache<T> {
    @Override
    public void put(String key, File bitmap) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public Bitmap get(String key) {
        return null;
    }
}

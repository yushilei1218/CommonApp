package com.shileiyu.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.jakewharton.disklrucache.DiskLruCache;
import com.shileiyu.imageloader.file.ImageFile;

import java.io.File;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class DiskCache<T extends File> implements IBitmapCache<T> {
    private static DiskCache instance;
    private DiskLruCache mLruCache;

    private DiskCache(Context context) {


    }

    public static synchronized DiskCache instance(Context context) {
        if (instance == null) {
            instance = new DiskCache(context);
        }
        return instance;
    }

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

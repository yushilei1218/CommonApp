package com.shileiyu.imageloader.cache;


import android.graphics.Bitmap;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class MemoryCache<T extends Bitmap> implements IBitmapCache<T> {

    @Override
    public void put(String key, Bitmap content) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public Bitmap get(String key) {
        return null;
    }
}



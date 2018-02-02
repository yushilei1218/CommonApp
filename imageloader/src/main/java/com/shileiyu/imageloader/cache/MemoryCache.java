package com.shileiyu.imageloader.cache;


import android.graphics.Bitmap;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class MemoryCache<T extends Bitmap> implements IBitmapCache<T> {
    private BitmapLruCache mLruCache = BitmapLruCache.instance();

    @Override
    public void put(String key, Bitmap content) {
        mLruCache.put(key, content);
    }

    @Override
    public void remove(String key) {
        mLruCache.remove(key);
    }

    @Override
    public Bitmap get(String key) {
        return mLruCache.get(key);
    }
}



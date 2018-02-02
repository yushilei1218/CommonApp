package com.shileiyu.imageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author shilei.yu
 * @since on 2018/2/2.
 */

public class BitmapLruCache extends LruCache<String, Bitmap> {

    private BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    private static BitmapLruCache instance;

    public static synchronized BitmapLruCache instance() {
        if (instance == null) {
            long maxSize = Runtime.getRuntime().maxMemory() / (1024 * 8);
            instance = new BitmapLruCache((int) maxSize);
        }
        return instance;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
    }
}

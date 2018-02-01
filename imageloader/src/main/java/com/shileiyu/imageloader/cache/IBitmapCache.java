package com.shileiyu.imageloader.cache;

import android.graphics.Bitmap;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public interface IBitmapCache<T> {

    void put(String key, T content);

    void remove(String key);

    Bitmap get(String key);
}

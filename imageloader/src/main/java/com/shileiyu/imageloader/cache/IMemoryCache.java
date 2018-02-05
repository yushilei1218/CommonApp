package com.shileiyu.imageloader.cache;

import android.graphics.Bitmap;

import com.shileiyu.imageloader.request.ImageRequest;

/**
 * @author shilei.yu
 * @since on 2018/2/5.
 */

public interface IMemoryCache {
    Bitmap get(ImageRequest request);

    void put(ImageRequest request, Bitmap bitmap);
}

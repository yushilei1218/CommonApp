package com.shileiyu.imageloader.cache;


import android.graphics.Bitmap;

import com.shileiyu.imageloader.request.ImageRequest;
import com.shileiyu.imageloader.util.Namer;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class MemoryCache implements IMemoryCache {
    private BitmapLruCache mLruCache = BitmapLruCache.instance();

    @Override
    public Bitmap get(ImageRequest request) {
        return mLruCache.get(Namer.key(request));
    }

    @Override
    public void put(ImageRequest request, Bitmap bitmap) {
        mLruCache.put(Namer.key(request), bitmap);
    }
}



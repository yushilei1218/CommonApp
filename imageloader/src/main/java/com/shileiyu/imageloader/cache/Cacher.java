package com.shileiyu.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shileiyu.imageloader.net.Downloader;
import com.shileiyu.imageloader.request.ImageRequest;
import com.shileiyu.imageloader.util.Util;


/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class Cacher {
    private static final String TAG = "Cacher";
    private static Cacher sCacher;

    private IDiskCache mDiskCache;
    private IMemoryCache mMemoryCache = new MemoryCache();

    private Cacher(Context context) {
        mDiskCache = new DiskCache(context);
    }

    public static synchronized Cacher instance(Context context) {
        if (sCacher == null) {
            sCacher = new Cacher(context);
        }
        return sCacher;
    }

    public void load(final ImageRequest request, final BitmapListener listener) {
        Log.d(TAG, "load --");
        //内存获取
        Bitmap bitmap = mMemoryCache.get(request);
        final BitmapListener wrap = new BitmapWrapListener(listener);
        if (bitmap == null) {
            //磁盘获取
            bitmap = mDiskCache.getBitmap(request);
            if (bitmap == null) {
                //网络获取
                wrap.onBitmap(State.LOADING, null);
                mDiskCache.download(request, new Downloader.DownloadListener() {
                    @Override
                    public void onResult(boolean isOk) {
                        if (isOk) {
                            load(request, listener);
                        } else {
                            wrap.onBitmap(State.NET_FAIL, null);
                        }
                    }
                });
            } else {
                mMemoryCache.put(request, bitmap);
            }
        }

        if (bitmap != null) {
            wrap.onBitmap(State.HAS, bitmap);
        }
    }

    private final class BitmapWrapListener implements BitmapListener {
        BitmapListener real;

        BitmapWrapListener(BitmapListener real) {
            this.real = real;
        }

        @Override
        public void onBitmap(final State state, @Nullable final Bitmap bitmap) {
            if (Util.isMainThread()) {
                real.onBitmap(state, bitmap);
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        real.onBitmap(state, bitmap);
                    }
                });
            }
        }
    }

    public interface BitmapListener {
        void onBitmap(State state, @Nullable Bitmap bitmap);
    }

    public enum State {
        LOADING,
        HAS,
        NET_FAIL
    }
}

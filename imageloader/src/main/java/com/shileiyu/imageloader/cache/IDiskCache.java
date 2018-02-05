package com.shileiyu.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.jakewharton.disklrucache.DiskLruCache;
import com.shileiyu.imageloader.file.ImageFile;
import com.shileiyu.imageloader.net.Downloader;
import com.shileiyu.imageloader.request.ImageRequest;

import java.io.File;
import java.io.OutputStream;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public interface IDiskCache {
    DiskLruCache.Editor getOps(String key);

    Bitmap getBitmap(ImageRequest request);

    void download(ImageRequest request, Downloader.DownloadListener downloadListener);
}

package com.shileiyu.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;
import com.shileiyu.imageloader.file.ImageFile;
import com.shileiyu.imageloader.net.Downloader;
import com.shileiyu.imageloader.request.ImageRequest;
import com.shileiyu.imageloader.util.BitmapUtil;
import com.shileiyu.imageloader.util.Namer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author shilei.yu
 * @since on 2018/2/5.
 */

public class DiskCache implements IDiskCache {
    private DiskLruCache mDiskLruCache;

    public DiskCache(Context context) {
        ImageFile imageFile = new ImageFile(context);
        try {
            mDiskLruCache = DiskLruCache.open(imageFile.getDir(), 1, 1, 1024 * 1024 * 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized DiskLruCache.Editor getOps(String key) {
        try {
            return mDiskLruCache.edit(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized Bitmap getBitmap(ImageRequest request) {

        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(Namer.key2DiskKey(request.getUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (snapshot == null) {
            return null;
        }
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = snapshot.edit().newInputStream(0);
            bitmap = BitmapFactory.decodeStream(inputStream, null, BitmapUtil.getOps(inputStream, request));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    @Override
    public void download(ImageRequest request, final Downloader.DownloadListener downloadListener) {
        String url = request.getUrl();
        final DiskLruCache.Editor editor = getOps(Namer.key2DiskKey(url));
        if (editor == null) {
            downloadListener.onResult(false);
            return;
        }
        OutputStream ops = null;
        try {
            ops = editor.newOutputStream(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ops != null) {
            Downloader.download(url, ops, new Downloader.DownloadListener() {
                @Override
                public void onResult(boolean isOk) {
                    if (isOk) {
                        try {
                            editor.commit();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            editor.abort();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    downloadListener.onResult(isOk);
                }
            });
        } else {
            downloadListener.onResult(false);
        }
    }
}

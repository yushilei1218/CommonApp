package com.yushilei.commonapp.ui.weex.cache;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.yushilei.commonapp.common.async.ThreadPools;
import com.yushilei.commonapp.common.file.FileUtil;
import com.yushilei.commonapp.common.net.Client;
import com.yushilei.commonapp.common.util.MD5;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author shilei.yu
 * @since 2017/12/1
 */

public class JsBundleCache {

    private static JsBundleCache instance;

    private final LruCache<String, String> mLruCache;

    private JsBundleCache() {
        mLruCache = new LruCache<>(1024);
    }

    public static synchronized JsBundleCache getInstance() {
        if (instance == null) {
            instance = new JsBundleCache();
        }
        return instance;
    }

    /**
     * 应该在子线程被调用
     *
     * @param weexUrl js bundle Url
     */
    public void obtain(final String weexUrl, final OnJsBundleListener listener) {
        ThreadPools.execute(new Runnable() {
            @Override
            public void run() {
                String digest = MD5.digest(weexUrl);
                Handler handler = new Handler(Looper.getMainLooper());
                //1.查内存缓存
                //   final String template = mLruCache.get(weexUrl);
                final String template = null;
                if (TextUtils.isEmpty(template)) {
                    //2.查文件缓存
                    boolean hasCache = false;
                    try {
                        File jsCacheDir = FileUtil.getJSCacheDir();
                        File[] files = jsCacheDir.listFiles();
                        File target = null;
                        if (files != null && files.length > 0) {
                            for (File sub : files) {
                                if (TextUtils.equals(sub.getName(), digest)) {
                                    target = sub;
                                    break;
                                }
                            }
                        }

                        if (target != null && target.exists()) {
                            hasCache = true;
                            final Uri uri = Uri.fromFile(target);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onUri(uri.toString());
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!hasCache) {
                        Request request = new Request.Builder().url(weexUrl).build();

                        try {
                            Response execute = Client.getClient().newCall(request).execute();
                            ResponseBody body = execute.body();
                            if (execute.isSuccessful() && body != null) {
                                final String template2 = body.string();
                                mLruCache.put(digest, template2);
                                FileOutputStream fos = null;
                                try {
                                    File file = new File(FileUtil.getJSCacheDir(), digest);
                                    fos = new FileOutputStream(file);
                                    fos.write(template2.getBytes(Charset.forName("UTF-8")));
                                    fos.close();
                                } finally {
                                    if (fos != null) {
                                        try {
                                            fos.close();
                                        } catch (Exception e) {

                                        }
                                    }
                                }

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onTemplate(template2);
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onFail();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail();
                                }
                            });
                        }
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onTemplate(template);
                        }
                    });

                }
            }
        });

    }

    public interface OnJsBundleListener {
        /**
         * 文件地址
         *
         * @param uri 文件
         */
        void onUri(String uri);

        /**
         * 获取到Js Bundle 字符串
         *
         * @param template Js Bundle 字符串
         */
        void onTemplate(String template);

        void onFail();
    }
}

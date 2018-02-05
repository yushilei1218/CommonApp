package com.shileiyu.imageloader.net;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.shileiyu.imageloader.util.Util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author shilei.yu
 * @since on 2018/2/2.
 */

public class Downloader {
    /**
     * 必须位于子线程中用于下载到指定的outputStream中
     *
     * @param urlStr 图片路径
     * @param ops    下载到指定的输出流
     * @return true下载完毕，false下载失败
     */
    private static boolean download2OutputStream(String urlStr, OutputStream ops) {
        HttpURLConnection connection = null;
        InputStream ins = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                ins = connection.getInputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = ins.read(temp)) != -1) {
                    ops.write(temp, 0, len);
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ops != null) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    /**
     * 触发下载，可在任意线程触发
     *
     * @param url      图片路径
     * @param ops      下载到指定的输出流
     * @param listener 下载回调
     */
    public static void download(String url, OutputStream ops, DownloadListener listener) {
        DownloadTask task = new DownloadTask(url, ops, listener);
        if (Util.isMainThread()) {
            //主线程
            ThreadPools.execute(task);
        } else {
            task.run();
        }
    }

    private static final class DownloadTask implements Runnable {
        private final String url;
        private final OutputStream ops;
        private final DownloadListener listener;

        DownloadTask(@NonNull String url, @NonNull OutputStream ops, @NonNull DownloadListener listener) {
            this.url = url;
            this.ops = ops;
            this.listener = listener;
        }

        @Override
        public void run() {
            try {
                boolean b = download2OutputStream(url, ops);
                listener.onResult(b);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface DownloadListener {
        /**
         * 下载回调，成功or失败
         *
         * @param isOk true成功
         */
        void onResult(boolean isOk);
    }
}

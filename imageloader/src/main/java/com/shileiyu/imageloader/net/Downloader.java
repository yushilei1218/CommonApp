package com.shileiyu.imageloader.net;


import android.os.Handler;
import android.os.HandlerThread;

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
    private HandlerThread ht;

    public static void downloadToOutputStream(String urlStr, OutputStream ops) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            connection.connect();
            InputStream ins = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

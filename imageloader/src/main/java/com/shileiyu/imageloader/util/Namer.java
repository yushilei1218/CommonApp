package com.shileiyu.imageloader.util;

import android.util.Base64;

import com.shileiyu.imageloader.request.ImageRequest;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class Namer {
    public static String key2name(String key) {
        Charset charset = Charset.forName("UTF-8");
        byte[] encode = Base64.encode(key.getBytes(charset), Base64.NO_WRAP);
        String name = new String(encode, charset);
        name = name.replace("/", ".");
        return name;
    }

    public static String key2DiskKey(String url) {
        return md5(url);
    }

    public static String key(ImageRequest request) {
        String key = request.getUrl() + "_" + request.getWidth() + "_" + request.getHeight();
        return key2name(key);
    }

    private static String md5(String key) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = key.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

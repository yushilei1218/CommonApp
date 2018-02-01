package com.shileiyu.imageloader.util;

import android.util.Base64;

import java.nio.charset.Charset;

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
}

package com.yushilei.commonapp.common.util;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author shilei.yu
 * @since 2017/12/1
 */

public class MD5 {
    public static String digest(String url) {
        String dig = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(url.getBytes());
            byte[] newArr = md5.digest();
            dig = Base64.encodeToString(newArr, 0, newArr.length, Base64.NO_WRAP);
            dig = dig.replaceAll("/", "+");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return dig;
    }
}

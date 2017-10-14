package com.yushilei.commonapp.common.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * @author shilei.yu
 * @since 2017/8/30
 */

public class JsonUtil {
    public static <T> T getObj(String json, Class<T> tClass) {
        return new Gson().fromJson(json, tClass);
    }

    public static <T> T getObj(InputStream ins, Class<T> tClass) {
        return new Gson().fromJson(new InputStreamReader(ins), tClass);

    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}

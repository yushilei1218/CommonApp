package com.yushilei.commonapp.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yushilei.commonapp.common.base.BaseApp;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 */

public class SpUtil {
    public static SharedPreferences getSp() {
        return BaseApp.AppContext.getSharedPreferences("key", Context.MODE_PRIVATE);
    }

    public static void save(String key, Object v) {
        SharedPreferences.Editor edit = getSp().edit();
        edit.putString(key, JsonUtil.toJson(v));
        edit.commit();
    }

    public static <T> T get(String key, Class<T> tClass) {
        String string = getSp().getString(key, "");
        if (TextUtils.isEmpty(string)) {
            return null;
        } else {
            return JsonUtil.getObj(string, tClass);
        }
    }
}

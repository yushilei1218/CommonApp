package com.yushilei.commonapp.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yushilei.commonapp.common.base.BaseApp;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 */

public class SpUtil {
    public static SharedPreferences getSp() {
        SharedPreferences sp = BaseApp.AppContext.getSharedPreferences("key", Context.MODE_PRIVATE);
        return sp;
    }

    public static void save(String key, Object v) {
        SharedPreferences.Editor edit = getSp().edit();
        if (v instanceof  String){
            edit.putString(key, (String) v);
        }else if (v instanceof Integer){

        }
        edit.commit();
    }
}

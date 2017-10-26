package com.yushilei.commonapp.common.bean.db;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yushilei.commonapp.common.util.JsonUtil;
import com.yushilei.commonapp.common.util.SetUtil;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author shilei.yu
 * @since 2017/10/26
 */

public class PersonTypeConverter implements PropertyConverter<List<String>, String> {

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (TextUtils.isEmpty(databaseValue)) {
            return null;
        }
        ArrayList<String> obj = JsonUtil.getObj(databaseValue, ArrayList.class);
        return obj;
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (SetUtil.isEmpty(entityProperty)) {
            return "";
        } else {
            return JsonUtil.toJson(entityProperty);
        }
    }
}

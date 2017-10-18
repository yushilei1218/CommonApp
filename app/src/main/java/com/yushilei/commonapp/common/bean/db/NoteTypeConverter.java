package com.yushilei.commonapp.common.bean.db;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @author shilei.yu
 * @since 2017/10/18
 */

public class NoteTypeConverter implements PropertyConverter<NoteType, String> {
    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}

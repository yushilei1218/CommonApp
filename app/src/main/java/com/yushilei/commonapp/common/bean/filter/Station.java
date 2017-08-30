package com.yushilei.commonapp.common.bean.filter;

import com.yushilei.commonapp.common.bean.net.Type;

/**
 * @author shilei.yu
 * @since 2017/8/30
 */

public class Station extends Location {
    int parentId;
    private final TypeEnum type = TypeEnum.STATION;

    @Override
    public TypeEnum getType() {
        return type;
    }
}

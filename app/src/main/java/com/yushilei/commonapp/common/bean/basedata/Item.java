package com.yushilei.commonapp.common.bean.basedata;

/**
 * @author shilei.yu
 * @since 2017/9/28
 */

public interface Item {
    String getName();

    int getId();

    int getParentId();

    boolean isSelect();

    void setSelect(boolean is);
}

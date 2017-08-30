package com.yushilei.commonapp.common.bean.filter;

/**
 * @author shilei.yu
 * @since 2017/8/30
 */

public interface IType {
    int getId();

    TypeEnum getType();

    boolean isHighLight();

    boolean isSelected();

    String getName();

    void setHighLight(boolean highLight);

    void setSelected(boolean selected);
}

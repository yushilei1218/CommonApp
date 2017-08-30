package com.yushilei.commonapp.common.bean.filter;

/**
 * @author shilei.yu
 * @since 2017/8/30
 */

public abstract class Location implements IType {
    private int id;
    private String name;
    private boolean isSelected;
    private boolean isHighLight;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isHighLight() {
        return isHighLight;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setHighLight(boolean highLight) {
        isHighLight = highLight;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

package com.yushilei.commonapp.common.bean.basedata;

/**
 * @author shilei.yu
 * @since 2017/9/28
 */

public class SubJobType implements Item {
    private int id;
    private int parentId;
    private String name;
    private boolean isSelect = false;
    private boolean isTag = false;

    public SubJobType(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    @Override
    public boolean isSelect() {
        return isSelect;
    }

    @Override
    public void setSelect(boolean is) {
        isSelect = is;
    }

    @Override
    public boolean isTag() {
        return isTag;
    }

    @Override
    public void setIsTag(boolean is) {
        isTag = is;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

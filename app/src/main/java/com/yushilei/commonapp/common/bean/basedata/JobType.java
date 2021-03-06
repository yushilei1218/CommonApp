package com.yushilei.commonapp.common.bean.basedata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/9/28
 */

public class JobType implements Item {
    private int id;
    private int parentId;
    private String name;
    private boolean isTag = false;

    private final List<SubJobType> mSubJobTypeList = new ArrayList<>(1034);
    private boolean isSelect = false;

    public JobType(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public void addSubJobType(SubJobType subJobType) {
        mSubJobTypeList.add(subJobType);

    }

    public List<SubJobType> getSubJobTypeList() {
        return mSubJobTypeList;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

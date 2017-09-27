package com.yushilei.commonapp.common.bean.basedata;

import java.util.ArrayList;
import java.util.List;

/**
 * 国家
 *
 * @author shilei.yu
 * @since 2017/9/27
 */

public class Country {
    private int id;
    private int parentId;
    private String name;
    private final List<Province> mProvinceList = new ArrayList<>();

    public Country(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public void addProvince(Province province) {
        mProvinceList.add(province);
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

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Province> getProvinceList() {
        return mProvinceList;
    }

}

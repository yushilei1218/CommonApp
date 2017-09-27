package com.yushilei.commonapp.common.bean.basedata;

import java.util.ArrayList;
import java.util.List;

/**
 * 省
 *
 * @author shilei.yu
 * @since 2017/9/27
 */

public class Province {
    private int id;
    private int parentId;
    private String name;
    private final List<City> mCityList = new ArrayList<>();

    public Province(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public void addCity(City city) {
        mCityList.add(city);
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

    public List<City> getCityList() {
        return mCityList;
    }
}

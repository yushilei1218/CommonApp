package com.yushilei.commonapp.common.bean.basedata;

/**
 * 市
 *
 * @author shilei.yu
 * @since 2017/9/27
 */

public class City {
    private int id;
    private int parentId;
    private String name;

    public City(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}

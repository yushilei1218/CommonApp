package com.yushilei.commonapp.ui.feizhu.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public class SortBean {
    private static SortBean sBean;
    public int id;
    public String name;

    public SortBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<SortBean> getList() {
        ArrayList<SortBean> data = new ArrayList<>();
        SortBean defaultBean = new SortBean(0, "推荐排序");
        sBean = defaultBean;
        data.add(defaultBean);
        data.add(new SortBean(1, "低价优先"));
        data.add(new SortBean(2, "高价优先"));
        data.add(new SortBean(3, "好评优先"));
        return data;
    }

    public boolean isSelected() {
        return sBean != null && sBean.equals(this);
    }

    public void setSelected() {
        sBean = this;
    }

    public static SortBean getSelected() {
        return sBean;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof SortBean) {
            return id == ((SortBean) obj).id;
        }
        return super.equals(obj);
    }
}

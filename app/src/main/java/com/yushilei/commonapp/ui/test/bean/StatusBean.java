package com.yushilei.commonapp.ui.test.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/7.
 */

public class StatusBean {
    private static final int ALL = 1;
    private static final int FILTER_PENDING = 2;
    private static final int CONTACT_PENDING = 3;
    private static final int INVITED = 4;

    private static StatusBean sBean;
    private int id;
    private String name;

    private StatusBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setSelected() {
        sBean = this;
    }

    public boolean isSelected() {
        return sBean != null && sBean == this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<StatusBean> get() {
        ArrayList<StatusBean> been = new ArrayList<>();
        been.add(new StatusBean(ALL, "全部来源"));
        StatusBean defaultBean = new StatusBean(FILTER_PENDING, "待筛选");
        sBean = defaultBean;
        been.add(defaultBean);
        been.add(new StatusBean(CONTACT_PENDING, "待沟通"));
        been.add(new StatusBean(INVITED, "已邀请"));
        return been;
    }

    public static StatusBean getBean() {
        return sBean;
    }
}

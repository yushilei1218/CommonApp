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
        return sBean != null && sBean.equals(this);
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
        been.add(defaultBean);
        been.add(new StatusBean(CONTACT_PENDING, "待沟通"));
        been.add(new StatusBean(INVITED, "已邀请"));
        if (sBean == null) {
            sBean = defaultBean;
        }
        return been;
    }

    public static List<StatusBean> getWithoutFilterPending() {
        ArrayList<StatusBean> been = new ArrayList<>();
        been.add(new StatusBean(ALL, "全部来源"));
        StatusBean defaultBean = new StatusBean(CONTACT_PENDING, "待沟通");
        been.add(defaultBean);
        been.add(new StatusBean(INVITED, "已邀请"));
        if (sBean != null) {
            if (!been.contains(sBean)) {
                sBean = defaultBean;
            }
        } else {
            sBean = defaultBean;
        }
        return been;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatusBean) {
            return id == ((StatusBean) obj).id;
        }
        return super.equals(obj);
    }

    public static StatusBean getBean() {
        return sBean;
    }
}

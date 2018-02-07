package com.yushilei.commonapp.ui.test.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/7.
 */

public class ResourceBean {
    private static final int ALL = 1;
    private static final int TOU_DI = 2;
    private static final int DOWNLOAD = 3;

    private static ResourceBean sBean;
    private int id;
    private String name;

    private ResourceBean(int id, String name) {
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

    public static List<ResourceBean> get() {
        ArrayList<ResourceBean> been = new ArrayList<>();
        been.add(new ResourceBean(ALL, "全部来源"));
        ResourceBean defaultBean = new ResourceBean(TOU_DI, "投递");
        sBean = defaultBean;
        been.add(defaultBean);
        been.add(new ResourceBean(DOWNLOAD, "下载"));
        return been;
    }

    public boolean isDownload() {
        return id == DOWNLOAD;
    }

    public static ResourceBean getBean() {
        return sBean;
    }
}

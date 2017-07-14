package com.yushilei.commonapp.common.bean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/14.
 */

public class BeanC {
    public String name;
    public List<BeanA> list;

    public BeanC(String name, List<BeanA> data) {
        this.name = name;
        this.list = data;
    }

}

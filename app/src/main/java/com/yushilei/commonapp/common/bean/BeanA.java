package com.yushilei.commonapp.common.bean;

/**
 * Created by shilei.yu on 2017/7/9.
 */

public class BeanA {
   public String name;

    public BeanA(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BeanA{" +
                "name='" + name + '\'' +
                '}';
    }
}

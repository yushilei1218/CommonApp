package com.yushilei.commonapp.common.annotation;

/**
 * @author shilei.yu
 * @since on 2018/3/12.
 */

public class Parent {
    @UseCase(id = 1, desc = "Parent public")
    public void parentA() {

    }

    @UseCase(id = 1, desc = "Parent protected")
    protected void parentB() {

    }

    @UseCase(id = 1, desc = "Parent private")
    private void parentC() {

    }

    @UseCase(id = 1, desc = "Parent static")
    public static void parentD() {

    }
}

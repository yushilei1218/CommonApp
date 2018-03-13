package com.yushilei.commonapp.common.annotation;

/**
 * @author shilei.yu
 * @since on 2018/3/12.
 */

public class Child extends Parent {
    @UseCase(id = 1, desc = "test method")
    public void test() {
        log("test");
    }

    @UseCase(id = 2)
    public void testB() {
        log("testB");
    }

    @UseCase(id = 3, desc = "static method")
    public static int testC() {
        return 1;
    }

    @UseCase(id = 4, desc = "private method")
    private void testD() {
        log("testD");
    }

    @UseCase(id = 5, desc = "protected method")
    protected void testE() {
        log("testE");
    }

    public void log(String msg) {
        System.out.println(msg);
    }
}

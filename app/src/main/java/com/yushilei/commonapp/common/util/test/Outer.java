package com.yushilei.commonapp.common.util.test;

/**
 * @author shilei.yu
 * @since on 2018/2/28.
 */

public class Outer {
    private int a = 1;
    int b = 2;
    protected int c = 3;
    public int d = 4;

    private void aa() {
    }

    void ab() {
    }

    protected void ac() {
    }

    public void ad() {

    }

    public Inner newInstance() {
        Inner inner = new Inner();
        inner.a = 1;
        inner.ma();
        inner.mb();
        inner.mc();
        inner.md();
        return inner;
    }

    public class Inner {
        private int a;

        private void ma() {
            a = 1;
            b = 2;
            c = 3;
            d = 4;
            aa();
            ab();
            ac();
            ad();
        }

        void mb() {
        }

        protected void mc() {
        }

        public void md() {
        }
    }
}

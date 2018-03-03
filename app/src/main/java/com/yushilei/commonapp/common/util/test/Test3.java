package com.yushilei.commonapp.common.util.test;


import com.yushilei.commonapp.common.util.Test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author shilei.yu
 * @since on 2018/2/26.
 */

public class Test3 extends Test {
    @Override
    public void a() {
        super.a();
    }

    @Override
    protected void b() {
        ArrayList list = new ArrayList();
        Iterator iterator = list.iterator();
        Outer outer = new Outer();
        Outer.Inner inner = outer.newInstance();
        inner.mc();
        inner.mb();
        inner.md();
        Outer.Inner inner1 = outer.new Inner();
        super.b();
        {
            class Bi extends Test {
                private int a;

                public Bi(int a) {
                    this.a = a;
                }
            }
            int a = new Bi(1).a;
        }
        // Can not int a = new Bi(1).a; here
    }
}

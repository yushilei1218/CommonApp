package com.yushilei.commonapp.common.bean.composite.child;

import com.yushilei.commonapp.common.bean.composite.Component;
import com.yushilei.commonapp.common.bean.composite.Composite;

/**
 * @author shilei.yu
 * @since on 2018/3/3.
 */

public class A_Country extends Composite {


    public A_Country(Component parent, Address address) {
        super(parent, address);
    }

    @Override
    public Composite newChild(Address address) {
        A_Province province = new A_Province(this,address);
        return province;
    }
}

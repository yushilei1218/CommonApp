package com.yushilei.commonapp.common.bean.composite.child;

import com.yushilei.commonapp.common.bean.composite.Component;
import com.yushilei.commonapp.common.bean.composite.Composite;

/**
 * @author shilei.yu
 * @since on 2018/3/3.
 */

public class Holder extends Composite {


    public Holder(Component parent, Address address) {
        super(parent, address);
    }

    @Override
    public Composite newChild(Address address) {
        return new A_Country(this,address);
    }
}

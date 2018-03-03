package com.yushilei.commonapp.common.bean.composite;

import com.yushilei.commonapp.common.bean.composite.child.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/3.
 */

public abstract class Composite extends Component {

    private ArrayList<Composite> childs = new ArrayList<>();

    public Composite(Component parent, Address address) {
        super(parent, address);
    }

    public void addChild(Composite component) {
        component.setParent(this);
        childs.add(component);
    }

    public void addChilds(List<Composite> children) {
        for (Composite c : children) {
            addChild(c);
        }
    }

    public void removeChild(Composite component) {
        childs.remove(component);
        component.setParent(null);
    }

    public List<Composite> getChildren() {
        return childs;
    }

    public boolean hasChild() {
        return true;
    }
}

package com.yushilei.commonapp.common.bean.composite;

import com.yushilei.commonapp.common.bean.composite.child.Address;

/**
 * @author shilei.yu
 * @since on 2018/3/3.
 */

public abstract class Component {
    private int parentId;

    private int id;

    private String name;

    protected Component parent = null;

    private boolean isFocus = false;

    private boolean isSelect = false;

    public Component() {
    }

    public Component(Component parent, Address address) {
        this.parent = parent;
        this.parentId = address.parentId;
        this.id = address.id;
        this.name = address.name;
    }

    public abstract Composite newChild(Address address);

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }
}

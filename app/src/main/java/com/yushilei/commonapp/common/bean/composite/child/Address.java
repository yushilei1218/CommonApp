package com.yushilei.commonapp.common.bean.composite.child;

import com.yushilei.commonapp.common.util.SetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/3.
 */

public class Address {
    public int id;
    public int parentId;
    public String name;

    public Address(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public static List<Address> getList(List<List<String>> data) {
        if (SetUtil.isEmpty(data)) {
            return null;
        }
        ArrayList<Address> temp = new ArrayList<>(data.size());
        for (List<String> d : data) {
            if (!SetUtil.isEmpty(d) && d.size() == 3) {
                temp.add(new Address(Integer.parseInt(d.get(0)), Integer.parseInt(d.get(1)), d.get(2)));
            }
        }
        return temp;
    }
}

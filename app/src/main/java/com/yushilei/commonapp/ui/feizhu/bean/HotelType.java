package com.yushilei.commonapp.ui.feizhu.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/9.
 */

public class HotelType {
    private static final List<HotelType> TYPES = new ArrayList<>();
    private static HotelType defaultBean = new HotelType(0, "不限");
    public int code;
    public String name;

    public HotelType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<HotelType> getList() {
        TYPES.clear();
        ArrayList<HotelType> data = new ArrayList<>();
        TYPES.add(defaultBean);
        data.add(defaultBean);
        data.add(new HotelType(1, "民宿/公寓"));
        data.add(new HotelType(2, "经济连锁"));
        data.add(new HotelType(3, "二星/其他"));
        data.add(new HotelType(4, "三星/舒适"));
        data.add(new HotelType(5, "四星/高档"));
        data.add(new HotelType(6, "五星/豪华"));
        return data;
    }

    public void setSelected() {
        if (code == 0) {
            TYPES.clear();
            TYPES.add(this);
        } else {
            if (TYPES.contains(this)) {
                TYPES.remove(this);
                if (TYPES.size() == 0) {
                    TYPES.add(defaultBean);
                }
            } else {
                TYPES.add(this);
                TYPES.remove(defaultBean);
            }
        }
    }

    public boolean isSelected() {
        return TYPES.contains(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof HotelType) {
            return code == ((HotelType) obj).code;
        }
        return super.equals(obj);
    }
}

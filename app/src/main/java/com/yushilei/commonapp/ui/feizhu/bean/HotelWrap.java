package com.yushilei.commonapp.ui.feizhu.bean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/9.
 */

public class HotelWrap {
    public List<HotelBean> data;
    public DataState state;

    public HotelWrap(List<HotelBean> data, DataState state) {
        this.data = data;
        this.state = state;
    }
}

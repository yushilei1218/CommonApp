package com.yushilei.commonapp.common.bean.filter;

import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/8/30
 */

public class SubWay extends Location {
    private final TypeEnum type = TypeEnum.SUBWAY;

    private List<Station> stations;

    @Override
    public TypeEnum getType() {
        return type;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}

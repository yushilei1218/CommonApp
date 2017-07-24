package com.yushilei.commonapp.common.bean.net;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 * <p>
 * ret: 0,
 * maxPageId: 5,
 * totalCount: 100,
 */

public class Recommend {
    private int ret;
    private int maxPageId;
    private List<Data> list;
    private int totalCount;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    public void setMaxPageId(int maxPageId) {
        this.maxPageId = maxPageId;
    }

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

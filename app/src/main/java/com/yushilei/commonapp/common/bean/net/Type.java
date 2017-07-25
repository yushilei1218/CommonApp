package com.yushilei.commonapp.common.bean.net;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 * bottomHasMore: true,
 * hasMore: true,
 * list: [],
 * loopCount: 0,
 * moduleType: "focus"
 */

public class Type {
    private boolean bottomHasMore;
    private boolean hasMore;
    private int loopCount;
    private String moduleType;
    private List<Data> list;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBottomHasMore() {
        return bottomHasMore;
    }

    public void setBottomHasMore(boolean bottomHasMore) {
        this.bottomHasMore = bottomHasMore;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }
}

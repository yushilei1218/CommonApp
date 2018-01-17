package com.yushilei.commonapp.common.bean.net;


import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/1/17.
 */

public class YouLike {
    private boolean hasMore;
    private String title;
    private int totalCount;
    private List<Album> list;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Album> getList() {
        return list;
    }

    public void setList(List<Album> list) {
        this.list = list;
    }
}

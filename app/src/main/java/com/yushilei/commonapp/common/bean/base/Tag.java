package com.yushilei.commonapp.common.bean.base;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 * <p>
 * bubbleText: "",
 * contentType: "rank_all_category",
 * contentUpdatedAt: 0,
 * coverPath: "http://fdfs.xmcdn.com/group28/M01/A6/11/wKgJXFlKDVGihjh4AAAbPYISwOY843.jpg",
 * enableShare: false,
 * id: 1458,
 * isExternalUrl: false,
 * sharePic: "",
 * subtitle: "",
 * title: "必听榜",
 * url: ""
 */

public interface Tag {
    public String getTitle();

    public void setTitle(String title);

    public String getSubtitle();

    public void setSubtitle(String subtitle);

    public String getBubbleText();

    public void setBubbleText(String bubbleText);

    public String getContentType();

    public void setContentType(String contentType);

    public int getContentUpdatedAt();

    public void setContentUpdatedAt(int contentUpdatedAt);

    public String getCoverPath();

    public void setCoverPath(String coverPath);

    public boolean isEnableShare();

    public void setEnableShare(boolean enableShare);

    public int getId();

    public void setId(int id);

    public boolean isExternalUrl();

    public void setExternalUrl(boolean externalUrl);

    public String getSharePic();

    public void setSharePic(String sharePic);
}

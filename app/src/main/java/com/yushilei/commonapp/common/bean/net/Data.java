package com.yushilei.commonapp.common.bean.net;

import com.yushilei.commonapp.common.bean.base.Album;
import com.yushilei.commonapp.common.bean.base.Focus;
import com.yushilei.commonapp.common.bean.base.IFocus;
import com.yushilei.commonapp.common.bean.base.Tag;
import com.yushilei.commonapp.common.bean.base.Topic;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class Data implements Album, Tag, Topic, IFocus {
    /**/
    private int albumId;
    private String infoType;
    private String pic;

    private String title;
    private String subtitle;
    /**/
    private String bubbleText;
    private String contentType;
    private int contentUpdatedAt;
    private String coverPath;
    private boolean enableShare;
    private int id;
    private boolean isExternalUrl;
    private String sharePic;

    /*topic*/
    private int columnType;
    private String footnote;
    private int specialId;

    /**/
    private List<Focus> data;
    private long responseId;
    private int ret;

    public List<Focus> getData() {
        return data;
    }

    public void setData(List<Focus> data) {
        this.data = data;
    }

    public long getResponseId() {
        return responseId;
    }

    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public String getFootnote() {
        return footnote;
    }

    public void setFootnote(String footnote) {
        this.footnote = footnote;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBubbleText() {
        return bubbleText;
    }

    public void setBubbleText(String bubbleText) {
        this.bubbleText = bubbleText;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentUpdatedAt() {
        return contentUpdatedAt;
    }

    public void setContentUpdatedAt(int contentUpdatedAt) {
        this.contentUpdatedAt = contentUpdatedAt;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public boolean isEnableShare() {
        return enableShare;
    }

    public void setEnableShare(boolean enableShare) {
        this.enableShare = enableShare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExternalUrl() {
        return isExternalUrl;
    }

    public void setExternalUrl(boolean externalUrl) {
        isExternalUrl = externalUrl;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }
}

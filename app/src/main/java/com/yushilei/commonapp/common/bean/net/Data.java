package com.yushilei.commonapp.common.bean.net;

import com.yushilei.commonapp.common.bean.base.Album;
import com.yushilei.commonapp.common.bean.base.Focus;
import com.yushilei.commonapp.common.bean.base.IFocus;
import com.yushilei.commonapp.common.bean.base.Square;
import com.yushilei.commonapp.common.bean.base.Topic;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class Data implements Album, Square, Topic, IFocus {
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

    @Override
    public List<Focus> getData() {
        return data;
    }

    @Override
    public void setData(List<Focus> data) {
        this.data = data;
    }

    @Override
    public long getResponseId() {
        return responseId;
    }

    @Override
    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }

    @Override
    public int getRet() {
        return ret;
    }

    @Override
    public void setRet(int ret) {
        this.ret = ret;
    }

    @Override
    public int getColumnType() {
        return columnType;
    }

    @Override
    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    @Override
    public String getFootnote() {
        return footnote;
    }

    @Override
    public void setFootnote(String footnote) {
        this.footnote = footnote;
    }

    @Override
    public int getSpecialId() {
        return specialId;
    }

    @Override
    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    @Override
    public int getAlbumId() {
        return albumId;
    }

    @Override
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    @Override
    public String getInfoType() {
        return infoType;
    }

    @Override
    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    @Override
    public String getPic() {
        return pic;
    }

    @Override
    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String getBubbleText() {
        return bubbleText;
    }

    @Override
    public void setBubbleText(String bubbleText) {
        this.bubbleText = bubbleText;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public int getContentUpdatedAt() {
        return contentUpdatedAt;
    }

    @Override
    public void setContentUpdatedAt(int contentUpdatedAt) {
        this.contentUpdatedAt = contentUpdatedAt;
    }

    @Override
    public String getCoverPath() {
        return coverPath;
    }

    @Override
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    @Override
    public boolean isEnableShare() {
        return enableShare;
    }

    @Override
    public void setEnableShare(boolean enableShare) {
        this.enableShare = enableShare;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isExternalUrl() {
        return isExternalUrl;
    }

    @Override
    public void setExternalUrl(boolean externalUrl) {
        isExternalUrl = externalUrl;
    }

    @Override
    public String getSharePic() {
        return sharePic;
    }

    @Override
    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }
}

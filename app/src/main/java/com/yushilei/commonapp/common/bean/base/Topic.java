package com.yushilei.commonapp.common.bean.base;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public interface Topic {
    public String getTitle();

    public void setTitle(String title);

    public String getSubtitle();

    public void setSubtitle(String subtitle);

    public int getColumnType();

    public void setColumnType(int columnType);

    public String getFootnote();

    public void setFootnote(String footnote);

    public int getSpecialId();

    public void setSpecialId(int specialId);

    public String getCoverPath();

    public void setCoverPath(String coverPath);

    public String getContentType();

    public void setContentType(String contentType);
}

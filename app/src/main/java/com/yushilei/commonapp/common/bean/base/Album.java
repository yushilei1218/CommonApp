package com.yushilei.commonapp.common.bean.base;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 * albumId: 247884,
 * infoType: "play_or_score",
 * isDraft: false,
 * isFinished: 0,
 * isPaid: false,
 * isVipFree: false,
 * lastUptrackAt: 1500510024000,
 * pic: "http://fdfs.xmcdn.com/group22/M00/9C/A0/wKgJM1hH29fzXhsiAAjQeG2D1U0541_mobile_large.jpg",
 * playsCount: 48302590,
 * recSrc: "UH",
 * recTrack: "gylM.102",
 * refundSupportType: 0,
 * subtitle: "出国旅游不可不备的英语指南",
 * title: "学英语环游世界",
 * tracksCount: 508
 */

public interface Album {
    public int getAlbumId();

    public void setAlbumId(int albumId);

    public String getInfoType();

    public void setInfoType(String infoType);

    public String getPic();

    public void setPic(String pic);

    public String getTitle();

    public void setTitle(String title);

    public String getSubtitle();
    public void setSubtitle(String subtitle);

}

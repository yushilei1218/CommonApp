package com.yushilei.commonapp.common.bean.base;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 * <p>
 * adId: 26721,
 * adType: 0,
 * clickTokens: [],
 * clickType: 1,
 * cover: "http://fdfs.xmcdn.com/group31/M09/2C/00/wKgJX1lwarThL-08AAIKBQCynxo627.jpg",
 * displayType: 1,
 * isAd: false,
 * isShareFlag: false,
 * link: "http://ad.ximalaya.com/adrecord?sdn=H4sIAAAAAAAAAKtWykhNTEktUrLKK83J0VFKzs_PzkyF8QoSixJzU0tSi4qVrKqVElM8S1JzPVOUrJSMzMyNDJVqawGDalyuPwAAAA&ad=26721",
 * openlinkType: 0,
 * realLink: "iting://open?msg_type=13&album_id=8871908",
 * showTokens: [],
 * thirdClickStatUrls: [ ],
 * thirdShowStatUrls: [ ],
 * thirdStatUrl: "
 */

public class Focus {
    private int adType;
    private String cover;
    private int displayType;
    private String link;
    private String realLink;

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRealLink() {
        return realLink;
    }

    public void setRealLink(String realLink) {
        this.realLink = realLink;
    }
}

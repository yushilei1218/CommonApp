package com.yushilei.commonapp.ui.coordinatorLayout;

/**
 * @author shilei.yu
 * @since on 2018/1/19.
 */

public class Channel {
    private String title;
    private String tips;
    private String image_url;

    public Channel() {
    }

    public Channel(String title, String tips, String image_url) {
        this.title = title;
        this.tips = tips;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

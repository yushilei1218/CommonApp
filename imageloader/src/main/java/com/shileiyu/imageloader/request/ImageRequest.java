package com.shileiyu.imageloader.request;


import android.content.Context;
import android.widget.ImageView;

import com.shileiyu.imageloader.Connecter;

/**
 * @author shilei.yu
 * @since on 2018/2/5.
 */

public class ImageRequest {
    public static final int ORG_WIDTH = -1;
    public static final int ORG_HEIGHT = -1;
    public static final int NONE = -1;

    private Context mContext;
    private int width = ORG_WIDTH;
    private int height = ORG_HEIGHT;
    private boolean useMemory = true;
    private boolean useDisk = true;
    private boolean cancel = false;
    private int pleaceHolder = NONE;
    private int errorHolder = NONE;
    private String url;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isUseMemory() {
        return useMemory;
    }

    public boolean isUseDisk() {
        return useDisk;
    }

    public boolean isCancel() {
        return cancel;
    }

    public int getPleaceHolder() {
        return pleaceHolder;
    }

    public int getErrorHolder() {
        return errorHolder;
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return mContext;
    }

    public Connecter into(ImageView img) {
        Connecter connecter = new Connecter(this, img);
        connecter.connect();
        return connecter;
    }

    public ImageRequest(Builder builder) {
        mContext = builder.mContext;
        width = builder.width;
        height = builder.height;
        useMemory = builder.useMemory;
        useDisk = builder.useDisk;
        pleaceHolder = builder.pleaceHolder;
        errorHolder = builder.errorHolder;
        url = builder.url;
    }

    public static class Builder {
        private Context mContext;
        private int width = ORG_WIDTH;
        private int height = ORG_HEIGHT;
        private boolean useMemory = true;
        private boolean useDisk = true;
        private int pleaceHolder = NONE;
        private int errorHolder = NONE;
        private String url;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder override(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder useMemory(boolean use) {
            useMemory = use;
            return this;
        }

        public Builder useDisk(boolean use) {
            useDisk = use;
            return this;
        }

        public Builder pleaceHolder(int pleaceHolder) {
            this.pleaceHolder = pleaceHolder;
            return this;
        }

        public Builder errorHolder(int errorHolder) {
            this.errorHolder = errorHolder;
            return this;
        }

        public ImageRequest build() {
            return new ImageRequest(this);
        }
    }
}

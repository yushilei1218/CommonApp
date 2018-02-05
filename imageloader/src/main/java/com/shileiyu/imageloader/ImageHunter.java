package com.shileiyu.imageloader;

import android.content.Context;

import com.shileiyu.imageloader.request.ImageRequest;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public final class ImageHunter {

    public static ImageRequest.Builder with(Context context) {
        return new ImageRequest.Builder(context);
    }
}

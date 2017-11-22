package com.yushilei.commonapp.common.weex;

import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;
import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since 2017/11/21
 */

public class ImageAdapter implements IWXImgLoaderAdapter {
    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        Logger.d(url);
        view.setImageResource(R.mipmap.ic_head);
    }

}

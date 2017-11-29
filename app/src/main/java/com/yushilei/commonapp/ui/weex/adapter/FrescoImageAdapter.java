package com.yushilei.commonapp.ui.weex.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;
import com.yushilei.commonapp.common.base.BaseApp;

/**
 * @author shilei.yu
 * @since 2017/11/21
 */

public class FrescoImageAdapter implements IWXImgLoaderAdapter {
    private static final String TAG = "FrescoImageAdapter";

    @Override
    public void setImage(final String url, final ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        WXSDKManager.getInstance().postOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (view == null || view.getLayoutParams() == null) {
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                int width = view.getLayoutParams().width;
                int height = view.getLayoutParams().height;
                if (width <= 0 || height <= 0) {
                    return;
                }
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                        .setResizeOptions(new ResizeOptions(width, height))
                        .setProgressiveRenderingEnabled(true).build();
                Log.d(TAG, "View width=" + width + " height" + height + " " + view.toString());
                DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request, BaseApp.AppContext);
                dataSource.subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        view.setImageBitmap(bitmap);
                        Log.d(TAG, " Bitmap width=" + bitmap.getWidth() + " height" + bitmap.getHeight() + " " + view.toString());
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                        dataSource.close();
                    }
                }, UiThreadImmediateExecutorService.getInstance());

            }
        }, 0);

    }

}

package com.shileiyu.imageloader;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.shileiyu.imageloader.cache.Cacher;
import com.shileiyu.imageloader.request.ImageRequest;

/**
 * @author shilei.yu
 * @since on 2018/2/5.
 */

public class Connecter {
    private final ImageRequest mRequest;
    private final ImageView mView;

    public Connecter(ImageRequest request, ImageView view) {
        mRequest = request;
        mView = view;
    }

    public void connect() {
        Cacher.instance(mRequest.getContext()).load(mRequest, new Cacher.BitmapListener() {
            @Override
            public void onBitmap(Cacher.State state, @Nullable Bitmap bitmap) {
                switch (state) {
                    case LOADING:
                        int pleaceHolder = mRequest.getPleaceHolder();
                        if (pleaceHolder == -1) {
                            mView.setImageBitmap(null);
                        } else {
                            mView.setImageResource(pleaceHolder);
                        }
                        break;
                    case NET_FAIL:
                        int errorHolder = mRequest.getErrorHolder();
                        if (errorHolder == -1) {
                            mView.setImageBitmap(null);
                        } else {
                            mView.setImageResource(errorHolder);
                        }
                        break;
                    case HAS:
                        mView.setImageBitmap(bitmap);
                        break;
                    default:
                        mView.setImageBitmap(null);
                        break;
                }
            }
        });
    }

    public void cancel() {

    }
}

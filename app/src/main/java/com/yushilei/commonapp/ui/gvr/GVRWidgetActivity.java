package com.yushilei.commonapp.ui.gvr;


import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.shileiyu.imageloader.net.ThreadPools;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

import static com.google.vr.sdk.widgets.pano.VrPanoramaView.Options.TYPE_MONO;


public class GVRWidgetActivity extends BaseActivity {

    @BindView(R.id.act_vr_panorama)
    VrPanoramaView mPanoramaV;

    private boolean isLoadSuccess = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gvrwidget;
    }

    @Override
    public void initView() {
        mPanoramaV.setEventListener(new ActivityEventListener());
        ThreadPools.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream open = getAssets().open("andes.jpg");
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType= VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
                    mPanoramaV.loadImageFromBitmap(BitmapFactory.decodeStream(open), options);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        mPanoramaV.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPanoramaV.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        mPanoramaV.shutdown();
        super.onDestroy();
    }

    private final class ActivityEventListener extends VrPanoramaEventListener {
        @Override
        public void onLoadSuccess() {
            isLoadSuccess = true;
        }

        @Override
        public void onLoadError(String errorMessage) {
            isLoadSuccess = false;
            showToast(errorMessage);
        }
    }
}

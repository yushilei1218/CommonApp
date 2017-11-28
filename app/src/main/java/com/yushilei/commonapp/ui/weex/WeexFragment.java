package com.yushilei.commonapp.ui.weex;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseFragment;

/**
 * Weex Fragment基类
 *
 * @author shilei.yu
 */
public class WeexFragment extends BaseFragment {
    private static final String WEEX_URL = "WEEX_URL";
    private static final String H5_URL = "H5_URL";
    private static final String TAG = "WeexFragment";

    private WXSDKInstance mWXSDKInstance;

    private ViewGroup mContainer;

    public WeexFragment() {
    }

    public static WeexFragment newInstance(String weexUrl, String h5Url) {
        WeexFragment fg = new WeexFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WEEX_URL, weexUrl);
        bundle.putString(H5_URL, h5Url);
        return fg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_weex;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showLoading();

        mContainer = findView(R.id.fg_weex_container);

        mWXSDKInstance = new WXSDKInstance(getActivity());
        mWXSDKInstance.registerRenderListener(new IWXRenderListener() {
            @Override
            public void onViewCreated(WXSDKInstance instance, View view) {
                log("onViewCreated");
                if (mContainer != null) {
                    hideLoading();
                    mContainer.addView(view);
                }
            }

            @Override
            public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
                hideLoading();
                log("onRenderSuccess");
            }

            @Override
            public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
                hideLoading();
                log("onRefreshSuccess");
            }

            @Override
            public void onException(WXSDKInstance instance, String errCode, String msg) {
                log("onException " + errCode + " " + msg);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mWXSDKInstance.render("WXSample", WXFileUtils.loadFileOrAsset("hello.js", getActivity()), null, null, WXRenderStrategy.APPEND_ASYNC);
                mWXSDKInstance.renderByUrl("WXSample", "http://doc.zwwill.com/yanxuan/jsbundles/index.js", null, null, WXRenderStrategy.APPEND_ASYNC);
            }
        }, 1000);

    }

    private int log(String msg) {
        return Log.d(TAG, msg);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }
}

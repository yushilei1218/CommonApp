package com.yushilei.commonapp.ui.weex;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXPerformance;
import com.taobao.weex.common.WXRenderStrategy;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseFragment;
import com.yushilei.commonapp.common.util.TimeCompute;
import com.yushilei.commonapp.ui.weex.cache.JsBundleCache;

/**
 * Weex Fragment基类
 *
 * @author shilei.yu
 */
public class WeexFragment extends BaseFragment {
    private static final String WEEX_URL = "WEEX_URL";
    private static final String TAG = "WeexFragment";

    private String mWeexUrl;

    private WXSDKInstance mWXSDKInstance;

    private ViewGroup mContainer;
    private TimeCompute mCompute;

    public WeexFragment() {
    }

    public static WeexFragment newInstance(String weexUrl) {
        WeexFragment fg = new WeexFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WEEX_URL, weexUrl);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle target;
        if (savedInstanceState != null) {
            target = savedInstanceState;
        } else {
            target = getArguments();
        }
        mWeexUrl = target.getString(WEEX_URL);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(WEEX_URL, mWeexUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_weex;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCompute = new TimeCompute("Weex 加载");
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
                mCompute.end();
                log("onRenderSuccess");
            }

            @Override
            public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
                hideLoading();
                mCompute.end();
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
                JsBundleCache.OnJsBundleListener listener = new JsBundleCache.OnJsBundleListener() {
                    @Override
                    public void onUri(String uri) {
                        mWXSDKInstance.renderByUrl("WXSample", uri, null, null, WXRenderStrategy.APPEND_ASYNC);
                    }

                    @Override
                    public void onTemplate(String template) {
                        mWXSDKInstance.render("WXSample", template, null, null, WXRenderStrategy.APPEND_ASYNC);
                    }

                    @Override
                    public void onFail() {
                        showToast("下载失败");
                    }
                };
                mCompute.start();
                JsBundleCache.getInstance().obtain(mWeexUrl, listener);
            }
        }, 500);

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

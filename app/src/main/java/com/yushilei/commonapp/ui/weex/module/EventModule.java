package com.yushilei.commonapp.ui.weex.module;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.ui.weex.WeexActivity;

/**
 * @author shilei.yu
 * @since 2017/11/29
 */

public class EventModule extends WXModule {
    private static final String TAG = "EventModule";

    @JSMethod
    public void fn(String zpfeapi, JSCallback callback) {
        Log.d(TAG, zpfeapi);
    }

    @JSMethod
    public void weexGoToWeexOrWebViewByUrl(String json, JSCallback jsCallback) {

        final String url;
        JSONObject jsonObject = (JSONObject) JSON.parse(json);
//            MyApp.jsCallback = jsCallback;
        url = jsonObject.get("url") + "";
        WeexActivity.invoke(BaseApp.AppContext, url);

    }
}

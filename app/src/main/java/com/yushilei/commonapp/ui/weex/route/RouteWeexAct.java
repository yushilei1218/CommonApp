package com.yushilei.commonapp.ui.weex.route;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taobao.weex.bridge.JSCallback;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.ui.weex.WeexActivity;

import java.lang.ref.WeakReference;

/**
 * @author shilei.yu
 * @since 2017/12/1
 */

public class RouteWeexAct extends BaseRoute {
    public RouteWeexAct(String json, JSCallback callback) {
        super(json, callback);
    }

    @Override
    public void invoke() {
        JsBean<BeanA> obj = JSON.parseObject(getJson(), new TypeReference<JsBean<BeanA>>() {
        });
        WeexActivity.invoke(BaseApp.AppContext, obj.url);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                JSCallback callBack = getJsCallBack();
                if (callBack != null) {
                    callBack.invoke("测试 回调CallBack");
                } else {
                    Toast.makeText(BaseApp.AppContext, " CallBack null", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);
    }
}

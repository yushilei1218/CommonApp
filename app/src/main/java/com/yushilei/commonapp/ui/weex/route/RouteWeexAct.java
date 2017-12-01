package com.yushilei.commonapp.ui.weex.route;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taobao.weex.bridge.JSCallback;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.ui.weex.WeexActivity;

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
        JsBean<BeanA> obj = JSON.parseObject(json, new TypeReference<JsBean<BeanA>>() {
        });
        Toast.makeText(BaseApp.AppContext, obj.toString(), Toast.LENGTH_SHORT).show();
        WeexActivity.invoke(BaseApp.AppContext, obj.url);
    }
}

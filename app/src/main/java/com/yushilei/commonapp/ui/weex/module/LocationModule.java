package com.yushilei.commonapp.ui.weex.module;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shilei.yu
 * @since 2017/11/28
 */

public class LocationModule extends WXModule {
    @JSMethod
    public void getLocation(JSCallback callback) {
        //获取定位代码.....
        Map<String, String> data = new HashMap<>();
        data.put("x", "x");
        data.put("y", "y");
        //通知一次
        callback.invoke(data);
        //持续通知
        // callback.invokeAndKeepAlive(data);

        //invoke方法和invokeAndKeepAlive两个方法二选一
    }
}

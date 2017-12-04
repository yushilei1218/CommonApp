package com.yushilei.commonapp.ui.weex.route;

import com.taobao.weex.bridge.JSCallback;
import com.yushilei.commonapp.common.util.JsonUtil;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * 子类必须包含一个String +JSCallBack的public构造方法
 *
 * @author shilei.yu
 * @since 2017/12/1
 */

public abstract class BaseRoute {
    private static final HashMap<String, Class<? extends BaseRoute>> MAP = new HashMap<>();

    private String json;

    private JSCallback jSCallback;

    static {
        MAP.put(RConstant.ROUTE_KEY1, RouteWeexAct.class);
    }

    @SuppressWarnings("WeakerAccess")
    public BaseRoute(String json, JSCallback callback) {
        this.json = json;
        jSCallback = callback;
    }

    /**
     * 负责处理JS CallNative具体的实现
     */
    public abstract void invoke();

    protected JSCallback getJsCallBack() {
        return jSCallback;
    }

    protected String getJson() {
        return json;
    }

    public static BaseRoute newInstance(String json, JSCallback callback) {
        JsBean obj = JsonUtil.getObj(json, JsBean.class);
        BaseRoute iRoute = null;
        if (obj != null) {
            Class<? extends BaseRoute> target = MAP.get(obj.name);
            if (target == null) {
                return null;
            }
            try {
                Constructor<? extends BaseRoute> constructor = target.getConstructor(String.class, JSCallback.class);
                iRoute = constructor.newInstance(json, callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iRoute;
    }
}

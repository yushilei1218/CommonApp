package com.yushilei.commonapp.common.net;

import com.yushilei.commonapp.common.retrofit.CallPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import retrofit2.Call;

/**
 * API代理类，配合任务id动态生成 API 对象
 * <p>
 * 利用反射解决{@link com.yushilei.commonapp.common.retrofit.NetProxy}
 * 需要手动实现接口，并且添加Call入池的过程
 * <p>
 * 性能好的呢-麻烦~~ 性能低一点点的呢-简单~~ 具体使用哪个看业务吧
 *
 * @author shilei.yu
 * @since on 2017/7/26.
 */

public class ApiProxy {
    private ApiProxy() {
    }

    /**
     * 动态生成{@link com.yushilei.commonapp.common.net.NetApi.API} 对象
     *
     * @param taskId 任务id 一般是MVP 中V 的hashcode
     * @return NetApi.API 对象
     */
    public static NetApi.API get(int taskId) {
        Class<?>[] clz = new Class[1];
        clz[0] = NetApi.API.class;
        return (NetApi.API) Proxy.newProxyInstance(NetApi.api.getClass().getClassLoader()
                , clz
                , new TaskInvocationHandler(taskId));
    }

    private static class TaskInvocationHandler implements InvocationHandler {
        private int taskId;

        TaskInvocationHandler(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method realMethod = NetApi.api.getClass().getMethod(method.getName(), method.getParameterTypes());
            Call call = (Call) realMethod.invoke(NetApi.api, args);
            CallPool.addCall(call, taskId);
            return call;
        }
    }
}

package com.yushilei.commonapp.common.retrofit;

import android.util.Log;
import android.util.SparseArray;

import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.util.SetUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import retrofit2.Call;

/**
 * 网络请求池：
 * <p>
 * 使用方法：一般无需自己调用，请配合 {@link NetProxy} 、{@link CommonCallBack}、{@link SimpleCallBack}
 * 进行使用
 * <p>
 * NetProxy 负责将构建的Call 加入CallPool中，两个CallBack继承{@link BaseCallBack} 负责回调时移除
 *
 * @author shilei.yu
 * @see BasePresenter#removeTask()  负责在页面消耗之前cancel掉关联的所有Call
 * @since on 2017/7/25.
 */

public final class CallPool {
    private CallPool() {
    }

    private static final String TAG = "CallPool";

    private static final SparseArray<List<WeakReference<Call>>> pool = new SparseArray<>();

    static synchronized void addCall(Call call, int taskId) {
        int index = pool.indexOfKey(taskId);
        if (index < 0) {
            pool.put(taskId, new LinkedList<WeakReference<Call>>());
        }
        List<WeakReference<Call>> list = pool.get(taskId);
        boolean contains = false;
        for (WeakReference<Call> w : list) {
            if (w != null && w.get() != null && w.get().equals(call)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            Log.i(TAG, "addCall ok " + taskId+" "+call.toString());
            list.add(new WeakReference<Call>(call));
        }
    }

    static synchronized void removeCall(Call call) {
        for (int i = 0; i < pool.size(); i++) {
            int key = pool.keyAt(i);
            List<WeakReference<Call>> list = pool.get(key);
            if (SetUtil.isEmpty(list))
                break;
            Iterator<WeakReference<Call>> iterator = list.iterator();
            while (iterator.hasNext()) {
                WeakReference<Call> next = iterator.next();
                if (next.get() != null && next.get().equals(call)) {
                    Log.i(TAG, "removeCall ok "+call.toString());
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 取消某个taskId下所有网络请求
     *
     * @param taskId 任务id
     */
    public static synchronized void cancelCall(int taskId) {
        List<WeakReference<Call>> set = pool.get(taskId);
        if (!SetUtil.isEmpty(set)) {
            for (WeakReference<Call> w : set) {
                if (w != null && w.get() != null) {
                    w.get().cancel();
                    Log.i(TAG, "cancelCall ok " + taskId+" "+w.get().toString());
                }
            }
        }
        pool.delete(taskId);
    }
}

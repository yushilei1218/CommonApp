package com.yushilei.commonapp.common.net.pool;


import android.util.Log;
import android.util.SparseArray;

import com.yushilei.commonapp.common.util.SetUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author shilei.yu
 * @since on 2018/1/17.
 */

public class NetPool {
    private static final SparseArray<List<DisposeWeakReference>> pool = new SparseArray<>();

    public static synchronized void add(int taskId, Disposable dispose) {
        if (dispose == null) {
            return;
        }
        Log.d("NetPool", "add");
        int index = pool.indexOfKey(taskId);
        if (index < 0) {
            pool.put(taskId, new ArrayList<DisposeWeakReference>());
        }
        List<DisposeWeakReference> list = pool.get(taskId);
        DisposeWeakReference l = new DisposeWeakReference<>(dispose);
        if (!list.contains(l)) {
            list.add(l);
        }
    }

    public static synchronized void remove(int taskId, Disposable dispose) {
        if (dispose == null) {
            return;
        }
        Log.d("NetPool", "remove");
        List<DisposeWeakReference> list = pool.get(taskId);
        if (!SetUtil.isEmpty(list)) {
            int index = list.indexOf(new DisposeWeakReference<>(dispose));
            if (index >= 0) {
                list.remove(index);
            }
            if (SetUtil.isEmpty(list)) {
                pool.delete(taskId);
            }
        }
    }

    public static synchronized void cancelAll(int taskId) {
        List<DisposeWeakReference> list = pool.get(taskId);
        if (!SetUtil.isEmpty(list)) {
            for (DisposeWeakReference d : list) {
                Disposable disposable = d.get();
                if (disposable != null) {
                    disposable.dispose();
                }
            }
            pool.delete(taskId);
        }
    }

    private static final class DisposeWeakReference<T extends Disposable> extends WeakReference<T> {

        DisposeWeakReference(T referent) {
            super(referent);
        }

        @Override
        public T get() {
            return super.get();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DisposeWeakReference) {
                DisposeWeakReference r = (DisposeWeakReference) obj;
                if (r.get() != null && get() != null) {
                    return r.get().equals(get());
                }
            }
            return super.equals(obj);
        }
    }
}

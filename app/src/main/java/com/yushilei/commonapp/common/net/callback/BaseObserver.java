package com.yushilei.commonapp.common.net.callback;


import com.yushilei.commonapp.common.net.ex.ApiException;
import com.yushilei.commonapp.common.net.ex.ExUtil;
import com.yushilei.commonapp.common.net.pool.NetPool;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author shilei.yu
 * @since on 2018/1/17.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    public final int taskId;
    private Disposable d;

    public BaseObserver(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.d = d;
        NetPool.add(taskId, d);
    }

    @Override
    public void onNext(@NonNull T t) {
        NetPool.remove(taskId, d);
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        NetPool.remove(taskId, d);
        ApiException transfer = ExUtil.transfer(e);
        onFail(transfer.code, transfer.message);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(@NonNull T t);

    public abstract void onFail(int code, String msg);
}

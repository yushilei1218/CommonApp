package com.yushilei.commonapp.ui.rxjava;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.async.ThreadPools;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.proxy.Function;
import com.yushilei.commonapp.common.proxy.Observable;
import com.yushilei.commonapp.common.proxy.Observer;

public class ProxyActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_proxy;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Observable<>("10", new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                log("apply " + s);
                return Integer.parseInt(s) + 10;
            }
        }).subscribeOnIO()
                .observeOnMain()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Integer data) {
                        log("onSubscribe " + data.toString());
                    }
                });
    }

    @Override
    public void initView() {

    }

    private void log(String msg) {
        Log.d(getTAG(), "线程=" + Thread.currentThread().getName() + " Mgs=" + msg);
    }
}

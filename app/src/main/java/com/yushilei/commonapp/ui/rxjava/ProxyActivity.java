package com.yushilei.commonapp.ui.rxjava;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.proxy.Observable;
import com.yushilei.commonapp.common.proxy.Observer;

public class ProxyActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_proxy;
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    @Override
    public void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Observer<Integer> observer = new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Integer data) {
                        log("onSubscribe =" + data.toString());
                    }
                };
                new Observable<String, Integer>("1") {

                    @Override
                    public Integer apply(String data) {
                        log("apply =" + data);
                        return Integer.parseInt(data);
                    }
                }.subscribe(new ProxyObserver(observer));
            }
        }).start();

    }

    private void log(String msg) {
        Log.d(getTAG(), Thread.currentThread().getName() + " " + msg);
    }

    public static final class ProxyObserver<T> implements Observer<T> {
        Observer<T> real;

        public ProxyObserver(Observer<T> real) {
            this.real = real;
        }

        @Override
        public void onSubscribe(final T data) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    real.onSubscribe(data);
                }
            });
        }
    }
}

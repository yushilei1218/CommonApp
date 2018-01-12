package com.yushilei.commonapp.ui.rxjava;


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

    @Override
    public void initView() {
        new Observable<String, Integer>("1") {

            @Override
            public Integer apply(String data) {
                return Integer.parseInt(data);
            }
        }.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Integer data) {
                Log.d(getTAG(), data.toString());
            }
        });
    }
}

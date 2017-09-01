package com.yushilei.commonapp.ui.myobservable;


import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.async.Observable;
import com.yushilei.commonapp.common.async.Observer;
import com.yushilei.commonapp.common.async.Task;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.net.DiscoveryBean;
import com.yushilei.commonapp.common.net.NetApi;

import retrofit2.Response;

public class ObservableActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_observable;
    }

    @Override
    public void initView() {
        setOnClick(R.id.act_observer_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_observer_btn:
                for (int i = 0; i < 10; i++) {
                    sendRequest();
                }
                break;
        }
    }

    private void sendRequest() {
        new Observable<DiscoveryBean>(new Task<DiscoveryBean>() {
            @Override
            public DiscoveryBean task() throws Throwable {
                Log.d(getTAG(), "task thread=" + Thread.currentThread().getName());
                Response<DiscoveryBean> execute = NetApi.api.getDiscovery().execute();
                return execute.body();
            }
        }).subscribe(new Observer<DiscoveryBean>() {
            @Override
            public void onComplete(DiscoveryBean data) {
                Log.d(getTAG(), "onComplete thread=" + Thread.currentThread().getName() + data.toString());
            }

            @Override
            public void onError(Throwable trx) {
                Log.d(getTAG(), "onError thread=" + Thread.currentThread().getName());
                trx.printStackTrace();
            }
        });
    }
}

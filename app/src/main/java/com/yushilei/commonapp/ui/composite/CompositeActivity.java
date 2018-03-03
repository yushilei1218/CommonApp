package com.yushilei.commonapp.ui.composite;


import com.yushilei.commonapp.R;

import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.bean.composite.child.Holder;
import com.yushilei.commonapp.common.util.BaseUtil;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class CompositeActivity extends BaseActivity {

    Holder mHolder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_composite;
    }

    @Override
    public void initView() {
        Flowable
                .fromCallable(new Callable<Holder>() {
                    @Override
                    public Holder call() throws Exception {
                        return BaseUtil.getHolder(BaseApp.AppContext);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Holder>() {
                    @Override
                    public void accept(Holder holder) throws Exception {
                        mHolder = holder;
                        showToast("完毕");
                    }
                });
    }
}

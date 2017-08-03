package com.yushilei.commonapp.ui.rxjava;


import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_java;
    }

    @Override
    public void initView() {
        GridView grid = (GridView) findView(R.id.rxjava_grid);
        MultiBaseAdapter adapter = new MultiBaseAdapter(1);
        grid.setAdapter(adapter);
        List<ItemWrapper> data = new LinkedList<>();
        data.add(new BeanWrapper(JUST));
        adapter.addAll(data);


    }

    public static final String JUST = "JUST";

    private void recordLog(String msg) {

    }

    private void just() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.i(getTAG(), "subscribe " + Thread.currentThread().getName());
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Log.i(getTAG(), "onNext " + integer + " " + Thread.currentThread().getName());
                        if (integer == 2) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(getTAG(), "onError " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(getTAG(), "onComplete ");
                    }
                });
    }

    private final class BeanWrapper extends ItemWrapper<String> implements View.OnClickListener {
        BeanWrapper(String bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_grid;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView tv = holder.findView(R.id.item_grid_tv);
            tv.setText(bean);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (bean) {
                case JUST:
                    just();
                    break;
            }
        }
    }
}

package com.yushilei.commonapp.ui.rxjava;


import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
        data.add(new BeanWrapper(MAP));
        adapter.addAll(data);


    }

    public static final String JUST = "JUST";
    public static final String MAP = "MAP";

    private void recordLog(String msg) {

    }

    private void just() {
        ArrayList<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        Flowable.fromIterable(data)

                .flatMap(new Function<String, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(@NonNull String s) throws Exception {
                        log("apply " + s);
                        SystemClock.sleep(200);
                        return Flowable.just(Integer.parseInt(s));
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log("accept " + integer);
                    }
                });


        if (true) {
            return;
        }
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

    private void log(String msg) {
        Log.d(getTAG(), "Thread=" + Thread.currentThread().getName() + " " + msg);
    }

    private Integer getInteger(String s) {
        log("2apply " + s);
        return Integer.parseInt(s);
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
                case MAP:
                    map();
                    break;
                default:
                    break;
            }
        }
    }

    private void map() {
        Observable.just(100).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer.toString();
            }
        }).flatMap(new Function<String, ObservableSource<BeanA>>() {
            @Override
            public ObservableSource<BeanA> apply(@NonNull String s) throws Exception {
                return Observable.just(new BeanA(s));
            }
        }).subscribe(new Observer<BeanA>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(getTAG(), "onSubscribe");
            }

            @Override
            public void onNext(@NonNull BeanA beanA) {
                Log.d(getTAG(), "onNext " + beanA.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(getTAG(), "onError ");
            }

            @Override
            public void onComplete() {
                Log.d(getTAG(), "onComplete ");
            }
        });
    }
}

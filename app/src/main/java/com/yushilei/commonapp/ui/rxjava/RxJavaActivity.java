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
import com.yushilei.commonapp.common.util.SpUtil;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    private TextView mtv;

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
        mtv = findView(R.id.act_rx_tv);

    }

    public static final String JUST = "JUST";
    public static final String MAP = "MAP";

    private void recordLog(String msg) {

    }

    private void just() {
        Observable<String> source1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                SystemClock.sleep(1000);
                log("source1");
                e.onNext("电话号=78310811571");
            }
        });
        Observable<Integer> source2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                SystemClock.sleep(2000);
                log("source2");
                e.onNext(100);
            }
        });
        Observable
                .zip(source1, source2, new BiFunction<String, Integer, String>() {
                    @Override
                    public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {

                        String msg = s + "  |  " + integer;
                        log(msg);
                        return msg;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        log(s);
                        mtv.setText(s);
                    }
                });
        if (true) {
            return;
        }
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                final Integer key = SpUtil.get("key", Integer.class);
                if (key != null && key == -1) {
                    //网络请求
                    e.onComplete();
                } else {
                    //显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text = "缓存显示 " + key;
                            mtv.setText(text);
                        }
                    });
                }
            }
        }, BackpressureStrategy.LATEST)
                .concatWith(Flowable.create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                        SystemClock.sleep(1000);

                        e.onNext(100);
                    }
                }, BackpressureStrategy.LATEST))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(final Integer integer) throws Exception {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String text = "网络请求显示 " + integer;
                                SpUtil.save("key", integer);
                                mtv.setText(text);
                            }
                        });
                    }
                });

        ArrayList<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        Flowable.fromIterable(data)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(@NonNull String s) throws Exception {
                        log("map  " + s);
                        return Integer.parseInt(s);
                    }
                })
                .flatMap(new Function<Integer, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(@NonNull Integer s) throws Exception {
                        int item = s + 10;
                        log("flatMap " + item);
                        SystemClock.sleep(200);
                        return Flowable.just(item);
                    }
                })
                .observeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log("doOnNext " + integer);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log("subscribe " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        log("Action ");
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

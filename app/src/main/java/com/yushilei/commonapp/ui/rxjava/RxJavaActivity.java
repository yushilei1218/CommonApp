package com.yushilei.commonapp.ui.rxjava;


import android.os.SystemClock;
import android.text.TextUtils;
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
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.net.NetApi;
import com.yushilei.commonapp.common.util.SpUtil;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.SafeSubscriber;

public class RxJavaActivity extends BaseActivity {

    private TextView mtv;
    private Disposable mDisposable;
    private Disposable mDisposable1;
    private Disposable mNetDisposable;

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
        data.add(new BeanWrapper(TEST));
        data.add(new BeanWrapper(PARALLEL));
        data.add(new BeanWrapper(SUBSCRIPTION));
        data.add(new BeanWrapper(CONCAT));
        adapter.addAll(data);
        mtv = findView(R.id.act_rx_tv);

    }

    public static final String JUST = "JUST";
    public static final String MAP = "MAP";
    public static final String TEST = "TEST";
    public static final String PARALLEL = "PARALLEL";
    private static final String SUBSCRIPTION = "SUBSCRIPTION";
    private static final String CONCAT = "CONCAT ";

    private void recordLog(String msg) {

    }

    private void just() {
        NetApi.sFlowapi.getFlowRecommend(1, 20)
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        log("doOnSubscribe");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RecommendBean>() {
                    @Override
                    public void accept(RecommendBean recommendBean) throws Exception {
                        log("Consumer<RecommendBean> ");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        log("Consumer<Throwable> ");
                    }
                });
        if (true) {
            return;
        }
        mDisposable = Flowable
                .interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        String msg = "" + aLong;
                        mtv.setText(msg);

                    }
                });
        if (true) {
            return;
        }
        //ZIP
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
        //CONCAT
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
        //MAP FLATMAP
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
                case TEST:
                    test();
                    break;
                case PARALLEL:
                    parallel();
                    break;
                case SUBSCRIPTION:
                    subscription();
                    break;
                case CONCAT:
                    concat();
                    break;
                default:
                    break;
            }
        }
    }

    private void concat() {
        Flowable<String> cacheFlowable = Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                        String msg = SpUtil.get("key2", String.class);
                        log("concat " + "subscribe" + msg);
                        if (TextUtils.isEmpty(msg)) {
                            e.onComplete();
                        } else {
                            e.onNext(msg);
                        }
                    }
                }, BackpressureStrategy.MISSING);

        Flowable<String> netFlowable = NetApi.sFlowapi
                .getFlowRecommend(1, 20)
                .map(new Function<RecommendBean, String>() {
                    @Override
                    public String apply(@NonNull RecommendBean recommendBean) throws Exception {
                        log("concat " + "apply");
                        if (recommendBean != null) {
                            return recommendBean.toString();
                        }
                        return null;
                    }
                });
        Flowable
                .concat(cacheFlowable, netFlowable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        SpUtil.save("key2", s);
                        log("concat " + "accept " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        log("concat " + "accept " + throwable.toString());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        log("concat " + "Action ");
                    }
                });
    }


    private void subscription() {
        Flowable.just("1").subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        ArrayList<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        Flowable.fromIterable(data).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                mtv.append("onSubscribe" + s.toString() + "\n");
            }

            @Override
            public void onNext(String s) {
                mtv.append("onNext " + s + "\n");
            }

            @Override
            public void onError(Throwable t) {
                mtv.append("onError " + t.toString() + "\n");
            }

            @Override
            public void onComplete() {
                mtv.append("onComplete" + "\n");
            }
        });
    }

    /**
     * 并行的
     */
    private void parallel() {
        NetApi.sFlowapi.getFlowRecommend(1, 20).flatMap(new Function<RecommendBean, Publisher<String>>() {
            @Override
            public Publisher<String> apply(@NonNull RecommendBean recommendBean) throws Exception {
                return Flowable.just(recommendBean.getMaxPageId() + "");
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(@NonNull String s) throws Exception {
                return null;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
        Flowable
                .range(1, 10)
                .parallel()
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        return integer + "";
                    }
                })
                .runOn(Schedulers.io())
                .sequential()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mtv.append(s + "\n");
                    }
                });

    }

    private void test() {
        Flowable.just("2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        log("accept " + s);
                    }
                });
        if (true) return;

        Flowable.just("1")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        log("onSubscribe");
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        log("onNext " + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        log("onError " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        log("onComplete ");
                    }
                });
        if (true) return;
        mNetDisposable = NetApi.sFlowapi.getFlowRecommend(1, 20)

                .map(new Function<RecommendBean, String>() {
                    @Override
                    public String apply(@NonNull RecommendBean recommendBean) throws Exception {
                        String s = "totalcount= " + recommendBean.getTotalCount() + " contentSize" + recommendBean.getList().size();
                        log("apply");
                        SystemClock.sleep(3000);
                        return s;
                    }
                })
                /**
                 * 新建Flowable 和Subscriber 在新Subscriber 回调中做线程切换-代理模式
                 *订阅发生后线程立刻发生变化，不管后面有几个subscribeOn最终会回到第一个subscribeOn的线程触发订阅开始
                 *也就是说在真正触发subscribe(Subscriber)时发生线程变化
                 */
                .subscribeOn(Schedulers.io())
                /**
                 *新建新建Flowable 和Subscriber 新subscriber会携带后续传入的真实subscriber
                 * 在新subscriber 回到方法中切换线程并调用真实subscriber的同名方法
                 * 所以observeOn 总是能使得紧跟其后的订阅发生在observeOn指定的线程里
                 */
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        log("accept");
                        showToast(s);
                        mtv.setText(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        log("accept");
                        String text = throwable.toString();
                        showToast(text);
                        mtv.setText(text);
                    }
                });
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

    @Override
    protected void onDestroy() {
        if (mNetDisposable != null) {
            mNetDisposable.dispose();
        }
        super.onDestroy();
    }
}

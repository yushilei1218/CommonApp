package com.yushilei.commonapp.common.async.scheduler;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.async.IObservable;
import com.yushilei.commonapp.common.async.Observer;

/**
 * @author shilei.yu
 * @since 2017/9/8
 */

public abstract class Observable<T> implements IObservable<T> {

    OnSubscribe mOnSubscribe;
    private T t;

    @Override

    public void subscribe(Observer<T> observer) {

    }

    public Observable<T> subscribeOn(Scheduler scheduler) {
        return new ObservableWithSubscribeScheduler<T>(this, scheduler);
    }

    public void observerOn(Scheduler scheduler) {

    }

    public final class OnSubscribe implements Function<T, R>, Runnable {

        @Override
        public R apply(T t) {
            return null;
        }

        @Override
        public void run() {
            try {
                R apply = apply(t);
            } catch (Throwable thx) {

            }
        }
    }

    private static final class ObservableWithSubscribeScheduler<T> extends Observable<T> {
        private Observable<T> parent;
        private Scheduler mScheduler;
        private Observer<T> mObserver;

        public ObservableWithSubscribeScheduler(Observable<T> parent, Scheduler scheduler) {
            this.parent = parent;
            mScheduler = scheduler;
        }

        @Override
        public void subscribe(Observer<T> observer) {
            mObserver = observer;
            mScheduler.execute(mOnSubscribe);
        }
    }
}

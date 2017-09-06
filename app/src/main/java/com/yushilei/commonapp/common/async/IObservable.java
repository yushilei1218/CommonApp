package com.yushilei.commonapp.common.async;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;

/**
 * @author shilei.yu
 * @since 2017/9/6
 */

public interface IObservable<T> {
    void subscribe(Observer<T> observer);
}

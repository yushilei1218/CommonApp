package com.yushilei.commonapp.common.retrofit;

import android.os.Build;
import android.support.annotation.RequiresApi;


/**
 * 自定义Exception
 *
 * @author shilei.yu
 * @since on 2017/7/25.
 */

public class IhrException extends Exception {
    public IhrException() {
    }

    public IhrException(String message) {
        super(message);
    }

    public IhrException(String message, Throwable cause) {
        super(message, cause);
    }

    public IhrException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public IhrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

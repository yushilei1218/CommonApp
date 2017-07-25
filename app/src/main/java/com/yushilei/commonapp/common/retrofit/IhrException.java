package com.yushilei.commonapp.common.retrofit;

import java.io.IOException;

/**
 * @author shilei.yu
 * @since on 2017/7/25.
 */

public class IhrException extends Exception{
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

    public IhrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

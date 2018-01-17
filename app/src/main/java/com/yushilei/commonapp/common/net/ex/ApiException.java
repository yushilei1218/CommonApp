package com.yushilei.commonapp.common.net.ex;

/**
 * @author shilei.yu
 * @since on 2018/1/17.
 */

public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}

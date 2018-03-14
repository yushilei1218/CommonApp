package com.yushilei.commonapp.common.bean;

/**
 * @author shilei.yu
 * @since on 2018/3/14.
 */

public class HttpResponse<T> {
    private int code;
    private T data;

    public HttpResponse() {
    }

    public HttpResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

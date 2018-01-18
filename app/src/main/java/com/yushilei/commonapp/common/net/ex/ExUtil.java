package com.yushilei.commonapp.common.net.ex;

import android.net.ParseException;

import com.alibaba.fastjson.JSONException;
import com.google.gson.JsonParseException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * @author shilei.yu
 * @since on 2018/1/17.
 */

public class ExUtil {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException transfer(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            //均视为网络错误
            return ex;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = "未知错误";
            //未知错误
            return ex;
        }
    }
}
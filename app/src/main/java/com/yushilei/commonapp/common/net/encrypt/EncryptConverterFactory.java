package com.yushilei.commonapp.common.net.encrypt;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 用于网络请求数据的加密和解密 ，需要配合{@link EncryptAnnotation} 进行使用
 * <p>
 * eg: API 方法上标注 @EncryptAnnotation
 * 1.会对 Http 响应体进行解密操作
 * 2.使用POST请求时才会对 请求体进行加密
 *
 * @author shilei.yu
 * @since on 2017/7/28.
 */

public class EncryptConverterFactory extends Converter.Factory {
    private static final String TAG = "EncryptConverterFactory";
    private static final boolean showLog = true;

    private Gson gson;

    private EncryptConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static EncryptConverterFactory create() {
        return new EncryptConverterFactory(new Gson());
    }

    /**
     * 利用 API中被标记{@link EncryptAnnotation} 注解的方法
     *
     * @param annotations API 方法上的所有注解
     * @return true:需要转换，false ：不需要
     */
    private boolean isNeedConverter(Annotation[] annotations) {
        boolean isNeedConverter = false;
        for (Annotation ann : annotations) {
            if (ann instanceof EncryptAnnotation) {
                isNeedConverter = true;
                break;
            }
        }
        return isNeedConverter;
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        boolean isNeedConverter = isNeedConverter(methodAnnotations);
        if (showLog) {
            Log.i(TAG, "requestBodyConverter isNeedConverter =" + isNeedConverter);
        }
        if (!isNeedConverter) {
            return null;
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new EncryptRequestBodyConverter<>(adapter);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        boolean needConverter = isNeedConverter(annotations);
        if (showLog) {
            Log.i(TAG, "responseBodyConverter needConverter=" + needConverter);
        }
        if (!needConverter) {
            return null;
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new DecryptResponseBodyConverter<>(adapter);
    }

    /**
     * 请求体加密Converter
     *
     * @param <T>
     */
    private final class EncryptRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json");
        private TypeAdapter<?> adapter;

        EncryptRequestBodyConverter(TypeAdapter<?> adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(@NonNull T value) throws IOException {
            String contentJson = gson.toJson(value);
            if (showLog) {
                Log.i(TAG, "RequestBody convert : " + new GsonBuilder().setPrettyPrinting().create().toJson(value));
            }
            /*加密过程......contentJson作为要加密的内容*/
            return RequestBody.create(MEDIA_TYPE, contentJson.getBytes(Charset.forName("UTF-8")));
        }
    }


    /**
     * 响应解密Converter
     *
     * @param <T>
     */
    private final class DecryptResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private TypeAdapter<T> adapter;

        DecryptResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(@NonNull ResponseBody value) throws IOException {
            String encryptContent = value.string();
            /*解密过程:encryptContent 是需要被解密的数据,解密完成后应该是Json数据*/
            if (showLog) {
                Log.i(TAG, "T convert : " + encryptContent);
            }

            return adapter.fromJson(encryptContent);
        }
    }
}

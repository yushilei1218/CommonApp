package com.yushilei.commonapp.common.net.encrypt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网络加密请求注解
 * <p>
 * 使用方法：{@link com.yushilei.commonapp.common.net.NetApi.API}
 * 定义的请求方法上加入该注解，在数据解析时会使用 {@link EncryptConverterFactory}
 *
 * @author shilei.yu
 * @since on 2017/7/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EncryptAnnotation {
}

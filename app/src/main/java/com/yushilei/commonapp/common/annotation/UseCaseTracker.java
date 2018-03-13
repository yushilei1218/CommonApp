package com.yushilei.commonapp.common.annotation;

import java.lang.reflect.Method;

/**
 * @author shilei.yu
 * @since on 2018/3/12.
 */

public class UseCaseTracker {
    public static void trackUseCase(Class<?> cl) {
        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            UseCase annotation = m.getAnnotation(UseCase.class);
            if (annotation != null) {
                String msg = "id= " + annotation.id() + " desc=" + annotation.desc();
                System.out.println(msg);
            }
        }
    }

    public static void main(String[] args) {
        trackUseCase(Child.class);
        System.out.println();
        trackUseCase(Child.class.getSuperclass());
    }
}

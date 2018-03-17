package com.yushilei.commonapp.common.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

/**
 * @author shilei.yu
 * @since on 2018/3/17.
 */

public class Procesor3 {
    public static void main(String[] args) {
        Class<A> aClass = A.class;
        try {
            Method getCall = aClass.getMethod("getCall");
            Class<?> returnType = getCall.getReturnType();
            TypeVariable<Method>[] typeParameters = getCall.getTypeParameters();
            Type genericReturnType = getCall.getGenericReturnType();

            System.out.println("returnType " + returnType);
            System.out.println("genericReturnType " + genericReturnType);
            System.out.println("genericReturnType class" + genericReturnType.getClass());
            System.out.println(Arrays.toString(typeParameters));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static class A {
        public B<List<BeanA>> getCall() {
            return new B<>();
        }
    }

    public static class B<T> {

    }
}

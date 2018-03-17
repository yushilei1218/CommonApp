package com.yushilei.commonapp.common.bean;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/15.
 */

public class Procesor2 {
    public static void main(String[] args) {

        Class<A> aClass = A.class;
        Class<B> bClass = B.class;
        Class<C> cClass = C.class;
        show(aClass);
        show(bClass);
        show(cClass);
    }

    private static void show(Class<?> c) {
        String name = c.getName();
        String tag = "---------" + name + "---------";
        System.out.println(tag);

        Type superclass = c.getGenericSuperclass();
        System.out.println("GenericSuperclass " + superclass);
        System.out.println("GenericSuperclass class " + superclass.getClass());
        System.out.println();

        showType(superclass);

        TypeVariable<? extends Class<?>>[] typeParameters = c.getTypeParameters();

        if (typeParameters.length > 0) {
            for (TypeVariable<? extends Class<?>> t : typeParameters) {
                System.out.println("TypeVariable Name" + t.getName());
                Type[] bounds = t.getBounds();
                for (Type type : bounds) {
                    System.out.println("TypeVariable bounds " + type.toString());
                    System.out.println("TypeVariable class " + type.getClass());
                }
            }
        }
        System.out.println(tag);
        System.out.println();
    }

    private static void showType(Type type) {
        if (type == null) {
            return;
        }
        System.out.println("Type " + type);
        System.out.println("Type class " + type.getClass());
        System.out.println();
        if (type instanceof ParameterizedType) {
            ParameterizedType type1 = (ParameterizedType) type;
            Type[] actualTypeArguments = type1.getActualTypeArguments();

            Type ownerType = type1.getOwnerType();
            Type rawType = type1.getRawType();
            showType(rawType);
            showType(ownerType);
            for (Type t : actualTypeArguments) {
                showType(t);
            }
        }
    }

    public static class A<T> {
        T a;

        public A(T a) {
            this.a = a;
        }
    }

    public static class B<T extends Number> extends A<T> {

        public B(T a) {
            super(a);
        }
    }

    public static class C extends A<List<BeanA>> {


        public C(List<BeanA> a) {
            super(a);
        }
    }
}

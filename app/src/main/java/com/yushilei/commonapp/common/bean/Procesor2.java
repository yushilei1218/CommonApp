package com.yushilei.commonapp.common.bean;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * @author shilei.yu
 * @since on 2018/3/15.
 */

public class Procesor2 {
    public static void main(String[] args) {
        class C<T extends BeanA> extends TypeToken<T> {

        }
        Class<C> cClass = C.class;
        TypeVariable<Class<C>>[] ctv = cClass.getTypeParameters();
        System.out.println(Arrays.toString(ctv));
        System.out.println(Arrays.toString(ctv[0].getBounds()));
        System.out.println();

        TypeToken<HttpResponse<BeanA>> token = new TypeToken<HttpResponse<BeanA>>() {
        };


        System.out.println();
        Class<B> bClass = B.class;
        TypeVariable<Class<B>>[] typeParameters = bClass.getTypeParameters();
        System.out.println(Arrays.toString(typeParameters));
        Type[] bounds = typeParameters[0].getBounds();
        System.out.println(Arrays.toString(bounds));


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
}

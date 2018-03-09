package com.yushilei.commonapp.common.bean.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

/**
 * @author shilei.yu
 * @since on 2018/3/9.
 */

public class Generic2 {
    public interface F {
        void f();
    }

    public interface K {
        void k();
    }

    public static class B {
        void b() {

        }
    }


    /**
     * 类型参数 最多继承一个类，实现多个接口，方式如下
     *
     * @param <T>
     */
    public static class Test2<T extends B & F & K> {
        T a;

        public Test2(T a) {
            this.a = a;
        }

        public void b() {
            a.b();
        }

        public void f() {
            a.f();
        }

        public void k() {
            a.k();
        }
    }

    private static class Test {

        public <T> T getT(Class<T> a) {
            try {
                return a.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void main(String[] a) {
        Queue queue = new LinkedList();
        Class<Collection> collectionClass = Collection.class;
        Class<String> stringClass = String.class;
        int[] arr = new int[1];
        Class<? extends int[]> aClass1 = arr.getClass();
        List<? extends A> list;
        List<EXTEND_A> list2 = new ArrayList<EXTEND_A>();
        list = list2;
        list2.add(new EXTEND_A());
        List<? super EXTEND_A> data = new ArrayList<>();

        TypeVariable<Class<H>>[] variables = H.class.getTypeParameters();
        System.out.println(Arrays.toString(variables));
        System.out.println(Arrays.toString(variables[0].getBounds()));


        Class<Test2> aClass = Test2.class;
        TypeVariable<Class<Test2>>[] parameters = aClass.getTypeParameters();
        System.out.println(Arrays.toString(parameters));
        Type[] bounds = parameters[0].getBounds();
        System.out.println(Arrays.toString(bounds));


        Test test = new Test();
        try {
            Method method = Test.class.getMethod("getT", Class.class);
            Object invoke = method.invoke(test, Test.class);
            Type type = method.getGenericReturnType();
            Class<?> declaringClass = method.getDeclaringClass();
            Object defaultValue = method.getDefaultValue();
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            Class<?>[] parameterTypes = method.getParameterTypes();
            TypeVariable<Method>[] typeParameters = method.getTypeParameters();

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public interface A {

    }

    public static class EXTEND_A implements A {
    }

    public interface C {

    }

    public static class G {

    }

    public static class D<T extends A> {
        T a;

        public D(T a) {
            this.a = a;
        }
    }

    public static class E<T extends C & A> extends D<T> {

        public E(T a) {
            super(a);
        }
    }

    public static class H<T extends G & C & A> extends E<T> {
        public H(T a) {
            super(a);
        }
    }
}

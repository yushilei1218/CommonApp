package com.yushilei.commonapp.common.bean.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/8.
 */

public class Generic {
    public interface F {
        void f();
    }

    public interface B {
        void b();
    }

    public static class Operator<T extends F> {
        T f;

        public Operator(T f) {
            this.f = f;
        }

        public void doF() {
            f.f();
        }
    }

    public static abstract class Parent<T extends Fruit> {
        public abstract T getT();
    }

    public static class Child extends Parent<Apple> {

        @Override
        public Apple getT() {
            return new Apple();
        }
    }

    public static void main(String[] a) {
        class FImpl implements F {
            @Override
            public void f() {
                System.out.println("FImpl f()");
            }
        }
        Child child = new Child();
        Class<?> superclass = child.getClass().getSuperclass();
        System.out.println(Arrays.toString(child.getClass().getTypeParameters()));
        TypeVariable<? extends Class<?>>[] parameters = superclass.getTypeParameters();
        System.out.println(Arrays.toString(parameters));
        System.out.println(Arrays.toString(parameters[0].getBounds()));
        System.out.println();
        Object invoke = null;
        Class<?> returnType = null;
        try {
            Method method = child.getClass().getMethod("getT");
            invoke = method.invoke(child);
            returnType = method.getReturnType();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<>();
        TypeVariable<? extends Class<? extends List>> typeVariable = list.getClass().getTypeParameters()[0];
        System.out.println(Arrays.toString(typeVariable.getBounds()));
        Operator<FImpl> operator = new Operator<>(new FImpl());
        operator.doF();
        TypeVariable<? extends Class<? extends Operator>>[] arr = operator.getClass().getTypeParameters();
        System.out.println(Arrays.toString(arr));
        TypeVariable<? extends Class<? extends Operator>> variable = arr[0];
        Class<? extends Operator> declaration = variable.getGenericDeclaration();
        System.out.println(declaration);
        Type[] bounds = variable.getBounds();
        System.out.println(Arrays.toString(bounds));
        Object o = new Object();
        System.out.println("Object " + Arrays.toString(o.getClass().getTypeParameters()));
    }
}

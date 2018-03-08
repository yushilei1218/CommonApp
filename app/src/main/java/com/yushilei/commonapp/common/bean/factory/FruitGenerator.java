package com.yushilei.commonapp.common.bean.factory;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shilei.yu
 * @since on 2018/3/8.
 */

public class FruitGenerator implements Generator<Fruit> {
    private FruitGenerator() {
    }

    @Override
    public Fruit next() {
        return null;
    }

    public <T> T getT(Class<T> a) throws IllegalAccessException, InstantiationException {
        return a.newInstance();
    }

    public static class Inner {
        public Inner() {
            FruitGenerator fruitGenerator = new FruitGenerator();
        }
    }

    public void test() {
    }
}

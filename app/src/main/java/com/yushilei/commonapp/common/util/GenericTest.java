package com.yushilei.commonapp.common.util;

import com.yushilei.commonapp.common.bean.factory.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shilei.yu
 * @since on 2018/3/8.
 */

public class GenericTest {
    public static void testGeneric() {
        List<String> list = new ArrayList<>();
        List list1 = new ArrayList();
        Map map = new HashMap();
        Map<String, Apple> map1 = new HashMap<>();
        log("List<String> " + Arrays.toString(list.getClass().getTypeParameters()));
        log("List " + Arrays.toString(list1.getClass().getTypeParameters()));
        log("map " + Arrays.toString(map.getClass().getTypeParameters()));
        log("Map<String, Apple> " + Arrays.toString(map1.getClass().getTypeParameters()));
    }

    public static void main(String[] arg) {
        testGeneric();
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}

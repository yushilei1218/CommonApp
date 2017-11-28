package com.yushilei.commonapp.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public class SetUtil {
    public static <T extends Collection> boolean isEmpty(T set) {
        return set == null || set.isEmpty();
    }

    public static <T extends Map> boolean isEmpty(T map) {
        return map == null || map.isEmpty();
    }
}

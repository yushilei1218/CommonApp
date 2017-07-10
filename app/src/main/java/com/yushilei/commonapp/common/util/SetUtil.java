package com.yushilei.commonapp.common.util;

import java.util.Collection;
import java.util.Set;

/**
 * @author shilei.yu
 * @since on 2017/7/10.
 */

public class SetUtil {
    public static <T extends Collection> boolean isEmpty(T set) {
        return set == null || set.isEmpty();
    }
}

package com.yushilei.commonapp.common.manager;

import com.yushilei.commonapp.common.base.BaseActivity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Activity维护栈
 */

public class ActivityStack {
    private ActivityStack() {
    }

    private static final Stack<BaseActivity> mStack = new Stack<>();

    public static void inStack(BaseActivity inActivity) {
        mStack.addElement(inActivity);
    }

    public static void outStack(BaseActivity outActivity) {
        mStack.remove(outActivity);
    }

    public static BaseActivity getTopActivity() {
        if (mStack.isEmpty())
            return null;
        return mStack.peek();
    }

    public static <T extends BaseActivity> void finish(Class<T> cls) {
        if (!mStack.isEmpty()) {
            for (BaseActivity next : mStack) {
                if (cls.isInstance(next)) {
                    next.finish();
                }
            }
        }
    }
}

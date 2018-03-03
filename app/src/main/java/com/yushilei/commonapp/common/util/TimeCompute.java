package com.yushilei.commonapp.common.util;

import android.util.Log;

import com.yushilei.commonapp.common.util.test.Test3;

/**
 * @auther by yushilei.
 * @time 2017/7/25-21:02
 * @desc
 */

public class TimeCompute {
    private final String TAG;

    public TimeCompute(String TAG) {
        this.TAG = TAG;
    }

    private long start = 0;
    private long end = 0;

    public void start() {
        Test a = new Test();
        a.a();
        Test2 test2 = new Test2();
        test2.c();
        Test3 test3 = new Test3();
        test3.a();
        start = System.currentTimeMillis();
        Log.d(TAG, TAG + "开始");
    }

    public void end() {
        end = System.currentTimeMillis();
        if (start > 0 && end > 0) {
            long l = end - start;

            Log.d(TAG, TAG + "结束,耗时=" + l + "毫秒");
        }
    }
}

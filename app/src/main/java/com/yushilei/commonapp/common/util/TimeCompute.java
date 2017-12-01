package com.yushilei.commonapp.common.util;

import android.util.Log;

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

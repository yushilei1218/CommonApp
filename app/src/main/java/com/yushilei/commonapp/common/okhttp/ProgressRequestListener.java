package com.yushilei.commonapp.common.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 请求体进度回调抽象类，比如用于文件上传中
 *
 * @author shilei.yu
 * @since on 2017/7/26.
 */

public abstract class ProgressRequestListener {

    private Progress mProgress = new Progress();

    private Handler mProgressHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            onMainRequestProgress(mProgress.bytesWritten, mProgress.contentLength, mProgress.done);
            return true;
        }
    });

    void onAsyncRequestProgress(long bytesWritten, long contentLength, boolean done) {
        mProgress.bytesWritten = bytesWritten;
        mProgress.contentLength = contentLength;
        mProgress.done = done;

        mProgressHandler.sendEmptyMessage(0x01);
    }

    abstract void onMainRequestProgress(long bytesWritten, long contentLength, boolean done);

    private class Progress {
        long bytesWritten;
        long contentLength;
        boolean done;
    }
}

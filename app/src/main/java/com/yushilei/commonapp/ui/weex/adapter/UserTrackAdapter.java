package com.yushilei.commonapp.ui.weex.adapter;

import android.content.Context;
import android.util.Log;

import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.common.WXPerformance;
import com.yushilei.commonapp.common.util.JsonUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * @author shilei.yu
 * @since 2017/11/28
 */

public class UserTrackAdapter implements IWXUserTrackAdapter {
    private static final String TAG = "UserTrackAdapter";

    @Override
    public void commit(Context context, String eventId, String type, WXPerformance perf, Map<String, Serializable> params) {
        String perfStr = JsonUtil.toJson(perf);
        // Log.d(TAG, "type=" + type + " " + perfStr);
    }
}

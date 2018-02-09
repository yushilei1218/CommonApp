package com.yushilei.commonapp.ui.feizhu;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.shileiyu.imageloader.net.ThreadPools;
import com.yushilei.commonapp.ui.feizhu.bean.DataState;
import com.yushilei.commonapp.ui.feizhu.bean.HotelBean;
import com.yushilei.commonapp.ui.feizhu.bean.HotelWrap;
import com.yushilei.commonapp.ui.feizhu.bean.SortBean;
import com.yushilei.commonapp.ui.feizhu.callback.CallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public class FeizhuModel implements FeizhuConstract.IModel {

    private final List<SortBean> mSorts = SortBean.getList();

    private final List<HotelBean> mData = new ArrayList<>();

    private int index = 0;

    @Override
    public List<SortBean> getSorts() {
        return mSorts;
    }

    @Override
    public void load(final boolean isRefresh, final CallBack<HotelWrap> callBack) {
        if (isRefresh) {
            index = 0;
        } else {
            index++;
        }
        final SortBean sortBean = SortBean.getSelected();
        ThreadPools.execute(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    mData.clear();
                }
                SystemClock.sleep(1000);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            mData.add(new HotelBean("洲际饭店 ++ index=" + index + " 第" + i + " " + sortBean.name));
                        }
                        callBack.callBack(new HotelWrap(mData, DataState.HAS_MORE));
                    }
                });
            }
        });
    }
}

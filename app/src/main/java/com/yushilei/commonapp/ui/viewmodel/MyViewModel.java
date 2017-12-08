package com.yushilei.commonapp.ui.viewmodel;

//import android.arch.lifecycle.LiveData;
//import android.arch.lifecycle.MutableLiveData;
//import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;
import android.util.Log;

import com.yushilei.commonapp.common.async.ThreadPools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/10/24
 */

public class MyViewModel extends Object {
//    private static final String TAG = "MyViewModel";
//    private MutableLiveData<List<String>> mLiveData;
//
//    public MyViewModel() {
//        super();
//        Log.d(TAG, "MyViewModel 构造函数");
//    }
//
//    public LiveData<List<String>> getLiveData() {
//        if (mLiveData == null) {
//            mLiveData = new MutableLiveData<>();
//            getString();
//        }
//        return mLiveData;
//    }
//
//    private void getString() {
//        ThreadPools.execute(new Runnable() {
//            @Override
//            public void run() {
//                SystemClock.sleep(2000);
//                List<String> data = new ArrayList<String>(10);
//                for (int i = 0; i < 10; i++) {
//                    data.add("item + " + i);
//                }
//                mLiveData.postValue(data);
//            }
//        });
//    }
//
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        Log.d(TAG, "MyViewModel onCleared");
//    }
}

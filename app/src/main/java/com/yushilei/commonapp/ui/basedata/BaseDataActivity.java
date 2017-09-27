package com.yushilei.commonapp.ui.basedata;


import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.basedata.BaseData;
import com.yushilei.commonapp.common.bean.basedata.City;
import com.yushilei.commonapp.common.bean.basedata.Country;
import com.yushilei.commonapp.common.bean.basedata.Province;
import com.yushilei.commonapp.common.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class BaseDataActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_data;
    }

    @Override
    public void initView() {
        View view = findView(R.id.act_base_data_format);
        setOnClick(R.id.act_base_data_format);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_base_data_format:
                format();
                break;
        }
    }

    private void format() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时 75毫秒
                long time1 = System.currentTimeMillis();
                InputStream inputStream = null;
                try {
                    inputStream = getAssets().open("baseData");
                    BaseData baseData = JsonUtil.getObj(inputStream, BaseData.class);
                    //487个城市
                    List<List<String>> city = baseData.getCity();

                    Country country = null;
                    for (int i = 0; i < city.size(); i++) {
                        List<String> strings = city.get(i);
                        boolean equals = strings.get(1).equals("0");
                        if (equals) {
                            int id = Integer.parseInt(strings.get(0));
                            int parentId = Integer.parseInt(strings.get(1));
                            country = new Country(id, parentId, strings.get(2));
                            break;
                        }
                    }
                    if (country != null) {
                        for (List<String> strs : city) {
                            String id = country.getId() + "";
                            if (strs.get(1).equals(id)) {
                                int childId = Integer.parseInt(strs.get(0));
                                int parentId = Integer.parseInt(strs.get(1));
                                Province province = new Province(childId, parentId, strs.get(2));
                                country.addProvince(province);
                            }
                        }

                        List<Province> provinceList = country.getProvinceList();
                        SparseArray<Province> provinceMap = new SparseArray<Province>();
                        for (int i = 0; i < provinceList.size(); i++) {
                            Province province = provinceList.get(i);
                            provinceMap.put(province.getId(), province);
                        }
                        for (int i = 0; i < city.size(); i++) {
                            List<String> strings = city.get(i);
                            int parentId = Integer.parseInt(strings.get(1));
                            Province province = provinceMap.get(parentId);
                            if (province != null) {
                                int id = Integer.parseInt(strings.get(0));
                                province.addCity(new City(id, parentId, strings.get(2)));
                            }
                        }
                        long time2 = System.currentTimeMillis();

                        Log.d(getTAG(), city.size() + " 耗时：" + (time2 - time1) + "毫秒");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null)
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }).start();
    }
}

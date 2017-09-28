package com.yushilei.commonapp.common.util;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.yushilei.commonapp.common.bean.basedata.BaseData;
import com.yushilei.commonapp.common.bean.basedata.City;
import com.yushilei.commonapp.common.bean.basedata.Country;
import com.yushilei.commonapp.common.bean.basedata.Job;
import com.yushilei.commonapp.common.bean.basedata.JobType;
import com.yushilei.commonapp.common.bean.basedata.JobTypeClass;
import com.yushilei.commonapp.common.bean.basedata.Province;
import com.yushilei.commonapp.common.bean.basedata.SubJobType;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/9/27
 */

public class BaseUtil {
    private static final String TAG = "BaseUtil";
    private static BaseData sBaseData = null;

    private synchronized static void init(Context context) {
        if (sBaseData != null) return;

        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("baseData");
            sBaseData = JsonUtil.getObj(inputStream, BaseData.class);
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

    public static Country getCountry(Context context) {
        Country country = null;
        //耗时 75毫秒
        long time1 = System.currentTimeMillis();
        init(context);
        //487个城市
        List<List<String>> city = sBaseData.getCity();

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

            Log.d(TAG, city.size() + " 耗时：" + (time2 - time1) + "毫秒");
        }

        return country;
    }

    public static Job getJob(Context context) {
        long time1 = System.currentTimeMillis();
        init(context);
        long time2 = System.currentTimeMillis();
        List<List<String>> jobtypeClass = sBaseData.getJobtypeClass();
        List<List<String>> jobTypes = sBaseData.getJobType();
        List<List<String>> subJobType = sBaseData.getSubJobType();
        Job job = new Job();
        SparseArray<JobTypeClass> jobClassMap = new SparseArray<>(13);
        SparseArray<JobType> jobTypeMap = new SparseArray<>(58);
        //行业分类
        for (List<String> item : jobtypeClass) {
            int id = Integer.parseInt(item.get(0));
            int parentId = Integer.parseInt(item.get(1));
            String name = item.get(2);
            JobTypeClass jobTypeClass = new JobTypeClass(id, parentId, name);
            job.addJobTypeClass(jobTypeClass);
            jobClassMap.put(id, jobTypeClass);
        }
        for (List<String> item : jobTypes) {
            int id = Integer.parseInt(item.get(0));
            int parentId = Integer.parseInt(item.get(1));
            String name = item.get(2);
            JobTypeClass jobTypeClass = jobClassMap.get(parentId);
            if (jobTypeClass != null) {
                JobType jobTypeItem = new JobType(id, parentId, name);
                jobTypeClass.addJobType(jobTypeItem);
                jobTypeMap.put(id, jobTypeItem);
            }
        }
        for (List<String> item : subJobType) {
            int id = Integer.parseInt(item.get(0));
            int parentId = Integer.parseInt(item.get(1));
            String name = item.get(2);
            JobType jobType = jobTypeMap.get(parentId);
            if (jobType != null) {
                jobType.addSubJobType(new SubJobType(id, parentId, name));
            }
        }
        long time3 = System.currentTimeMillis();
        Log.d(TAG, "getJob 耗时-" + (time3 - time2) + " 毫秒");
        return job;
    }
}

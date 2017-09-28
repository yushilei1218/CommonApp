package com.yushilei.commonapp.common.bean.basedata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/9/28
 */

public class Job {
    private final List<JobTypeClass> mTypeClassList = new ArrayList<>(13);

    public void addJobTypeClass(JobTypeClass jobTypeClass) {
        mTypeClassList.add(jobTypeClass);
    }

    public List<JobTypeClass> getTypeClassList() {
        return mTypeClassList;
    }
}

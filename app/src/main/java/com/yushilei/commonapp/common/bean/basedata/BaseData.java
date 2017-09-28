package com.yushilei.commonapp.common.bean.basedata;

import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/9/27
 */

public class BaseData {

    private List<List<String>> city;
    private List<List<String>> jobType;
    private List<List<String>> subJobType;
    private List<List<String>> jobtypeClass;

    public List<List<String>> getJobType() {
        return jobType;
    }

    public void setJobType(List<List<String>> jobType) {
        this.jobType = jobType;
    }

    public List<List<String>> getSubJobType() {
        return subJobType;
    }

    public void setSubJobType(List<List<String>> subJobType) {
        this.subJobType = subJobType;
    }

    public List<List<String>> getJobtypeClass() {
        return jobtypeClass;
    }

    public void setJobtypeClass(List<List<String>> jobtypeClass) {
        this.jobtypeClass = jobtypeClass;
    }

    public List<List<String>> getCity() {
        return city;
    }

    public void setCity(List<List<String>> city) {
        this.city = city;
    }
}

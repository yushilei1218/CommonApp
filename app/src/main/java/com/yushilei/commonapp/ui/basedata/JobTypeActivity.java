package com.yushilei.commonapp.ui.basedata;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.async.Observable;
import com.yushilei.commonapp.common.async.Observer;
import com.yushilei.commonapp.common.async.Task;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.basedata.Job;
import com.yushilei.commonapp.common.bean.basedata.JobType;
import com.yushilei.commonapp.common.bean.basedata.JobTypeClass;
import com.yushilei.commonapp.common.bean.basedata.SubJobType;
import com.yushilei.commonapp.common.util.BaseUtil;
import com.yushilei.commonapp.common.widget.LineItemDecoration;

import java.util.List;

public class JobTypeActivity extends BaseActivity {

    private MultiHolderAdapter mJobClassAdapter;
    private MultiHolderAdapter mJobTypeAdapter;
    private MultiHolderAdapter mSubJobAdapter;
    private JobTypeClass mTypeClass;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_type;
    }

    @Override
    public void initView() {
        //jobType 59个大类
        //subJobType  1034 个小类
        //jobtypeClass  13个行业
        setOnClick(R.id.act_job_type_get_job);

        RecyclerView jobClassRecycler = findView(R.id.act_job_class_recycler);
        jobClassRecycler.addItemDecoration(new LineItemDecoration(R.color.colorPrimary));
        RecyclerView jobTypeRecycler = findView(R.id.act_job_type_recycler);
        jobTypeRecycler.addItemDecoration(new LineItemDecoration(R.color.colorPrimary));
        RecyclerView jobSubRecycler = findView(R.id.act_job_sub_recycler);
        jobSubRecycler.addItemDecoration(new LineItemDecoration(R.color.colorPrimary));

        mJobClassAdapter = new MultiHolderAdapter();
        mJobClassAdapter.setMatch(JobTypeClass.class, new JobTypeClassDelegate());
        jobClassRecycler.setAdapter(mJobClassAdapter);

        mJobTypeAdapter = new MultiHolderAdapter();
        mJobTypeAdapter.setMatch(JobType.class, new JobTypeDelegate());
        jobTypeRecycler.setAdapter(mJobTypeAdapter);

        mSubJobAdapter = new MultiHolderAdapter();
        mSubJobAdapter.setMatch(SubJobType.class, new SubJobTypeDelegate());
        jobSubRecycler.setAdapter(mSubJobAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_job_type_get_job:
                getJob();
                break;
        }
    }

    private void getJob() {
        new Observable<>(new Task<Job>() {
            @Override
            public Job task() throws Throwable {
                return BaseUtil.getJob(getApplicationContext());
            }
        }).subscribe(new Observer<Job>() {
            @Override
            public void onComplete(Job data) {
                Log.d(getTAG(), "onComplete");
                initJob(data);
                mJobClassAdapter.replaceData(data.getTypeClassList());
            }

            @Override
            public void onError(Throwable trx) {

            }
        });
    }

    SparseArray<JobType> mJobTypeMap = new SparseArray<>();

    private void initJob(Job data) {
        //行业选中缓存
        List<JobTypeClass> typeClassList = data.getTypeClassList();
        mTypeClass = typeClassList.get(0);
        mTypeClass.setSelect(true);
        for (JobTypeClass jobClass : typeClassList) {
            List<JobType> jobTypeList = jobClass.getJobTypeList();
            JobType type = jobTypeList.get(0);
            type.setSelect(true);
            //一个行业对应一个大类默认呗选中
            mJobTypeMap.put(type.getParentId(), type);
        }

    }

    private final class JobTypeClassDelegate extends HolderDelegate<JobTypeClass> {

        @Override
        public int getLayoutId() {
            return R.layout.item_job;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, JobTypeClass data, int pos) {
            TextView view = (TextView) holder.findView(R.id.item_name);
            view.setText(data.getName());
            if (data.isSelect()) {
                mJobTypeAdapter.replaceData(data.getJobTypeList());
            }
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<JobTypeClass> holder, JobTypeClass jobTypeClass) {
            if (!jobTypeClass.isSelect()) {
                jobTypeClass.setSelect(true);
                mTypeClass.setSelect(false);
                mTypeClass = jobTypeClass;
                mJobClassAdapter.notifyDataSetChanged();
                showToast(jobTypeClass.getName());
            }
        }
    }

    private final class JobTypeDelegate extends HolderDelegate<JobType> {

        @Override
        public int getLayoutId() {
            return R.layout.item_job;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, JobType data, int pos) {
            TextView view = (TextView) holder.findView(R.id.item_name);
            view.setText(data.getName());
            if (data.isSelect()) {
                mSubJobAdapter.replaceData(data.getSubJobTypeList());
            }
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<JobType> holder, JobType jobType) {
            if (!jobType.isSelect()) {
                jobType.setSelect(true);
                JobType selectType = mJobTypeMap.get(jobType.getParentId());
                selectType.setSelect(false);
                mJobTypeMap.put(jobType.getParentId(), jobType);
                mJobClassAdapter.notifyDataSetChanged();
            }
            showToast(jobType.getName());
        }
    }

    private final class SubJobTypeDelegate extends HolderDelegate<SubJobType> {

        @Override
        public int getLayoutId() {
            return R.layout.item_job;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, SubJobType data, int pos) {
            TextView view = (TextView) holder.findView(R.id.item_name);
            view.setText(data.getName());
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<SubJobType> holder, SubJobType subJobType) {
            showToast(subJobType.getName());
        }
    }
}

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
    /**
     * 指向当前被选中的行业
     */
    private JobTypeClass mTypeClass;
    /**
     * 记录当前处于选中状态的2级列表数据
     */
    SparseArray<JobType> mParentIdJobTypeMap = new SparseArray<>();
    SparseArray<JobType> mIdJobTypeMap = new SparseArray<>();
    SparseArray<JobTypeClass> mIdJobClassMap = new SparseArray<>();
    /**
     *
     */
    SubJobType mSubJobType;

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

    private void initJob(Job data) {
        //行业选中缓存
        List<JobTypeClass> typeClassList = data.getTypeClassList();
        mTypeClass = typeClassList.get(0);
        mTypeClass.setSelect(true);
        for (JobTypeClass j : typeClassList) {
            mIdJobClassMap.put(j.getId(), j);
        }
        for (JobTypeClass jobClass : typeClassList) {
            List<JobType> jobTypeList = jobClass.getJobTypeList();
            for (int i = 0; i < jobTypeList.size(); i++) {
                JobType jobType = jobTypeList.get(i);
                mIdJobTypeMap.put(jobType.getId(), jobType);
            }
            JobType type = jobTypeList.get(0);
            type.setSelect(true);
            //一个行业对应一个大类默认呗选中
            mParentIdJobTypeMap.put(type.getParentId(), type);

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
            View tag = holder.findView(R.id.item_tag);
            view.setText(data.getName());
            if (data.isSelect()) {
                holder.itemView.setSelected(true);
                mJobTypeAdapter.replaceData(data.getJobTypeList());
            } else {
                holder.itemView.setSelected(false);
            }
            if (data.isTag()) {
                tag.setVisibility(View.VISIBLE);
            } else {
                tag.setVisibility(View.GONE);
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
            View tag = holder.findView(R.id.item_tag);
            view.setText(data.getName());
            if (data.isSelect()) {
                holder.itemView.setSelected(true);
                mSubJobAdapter.replaceData(data.getSubJobTypeList());
            } else {
                holder.itemView.setSelected(false);
            }
            if (data.isTag()) {
                tag.setVisibility(View.VISIBLE);
            } else {
                tag.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<JobType> holder, JobType jobType) {
            if (!jobType.isSelect()) {
                jobType.setSelect(true);
                JobType selectType = mParentIdJobTypeMap.get(jobType.getParentId());
                selectType.setSelect(false);
                mParentIdJobTypeMap.put(jobType.getParentId(), jobType);
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
            View tag = holder.findView(R.id.item_tag);
            view.setText(data.getName());
            holder.itemView.setOnClickListener(holder);
            if (data.isSelect()) {
                tag.setVisibility(View.VISIBLE);
                holder.itemView.setSelected(true);
            } else {
                tag.setVisibility(View.GONE);
                holder.itemView.setSelected(false);
            }

        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<SubJobType> holder, SubJobType subJobType) {
            showToast(subJobType.getName());
            if (subJobType.isSelect()) {
                subJobType.setSelect(false);
                //新增现在的
                JobType newJobType = mIdJobTypeMap.get(subJobType.getParentId());
                newJobType.setIsTag(false);
                mIdJobClassMap.get(newJobType.getParentId()).setIsTag(false);
                mSubJobType = null;
            } else {
                subJobType.setSelect(true);
                if (mSubJobType != null) {
                    mSubJobType.setSelect(false);
                    int oldParentId = mSubJobType.getParentId();
                    if (oldParentId != subJobType.getParentId()) {
                        //原数据对应的TAG要消除
                        JobType jobType = mIdJobTypeMap.get(oldParentId);
                        jobType.setIsTag(false);
                        mIdJobClassMap.get(jobType.getParentId()).setIsTag(false);
                    }
                }
                //新增现在的
                JobType newJobType = mIdJobTypeMap.get(subJobType.getParentId());
                newJobType.setIsTag(true);
                mIdJobClassMap.get(newJobType.getParentId()).setIsTag(true);
                mSubJobType = subJobType;
            }
            mJobClassAdapter.notifyDataSetChanged();
        }

    }
}

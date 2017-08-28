package com.yushilei.commonapp.ui.filter;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.common.widget.LoadListView;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends MvpBaseActivity<FilterContract.IPresenter> implements FilterContract.IView {

    private View mFormTv;
    private View mLocationTv;
    private LoadListView mLv;
    private ViewGroup mFilterContainer;
    private ViewStub mLocationViewStub;
    private ViewStub mFromViewStub;
    private View mFromLayout;
    private View mLocationLayout;

    @Override
    public FilterContract.IPresenter getPresenter() {
        return new FilterPresenter(this, new FilterModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter;
    }

    @Override
    public void initView() {
        mFromViewStub = findView(R.id.act_filter_from_view_stub);
        mLocationViewStub = findView(R.id.act_filter_location_view_stub);

        mFormTv = findView(R.id.act_filter_from);
        mLocationTv = findView(R.id.act_filter_location);
        mFilterContainer = findView(R.id.act_filter_container);
        mLv = findView(R.id.act_filter_lv);
        MultiListAdapter adapter = new MultiListAdapter(1);
        adapter.setMatch(BeanA.class, new BeanDelegate());
        mLv.setAdapter(adapter);

        adapter.addAll(get());

        setOnClick(mFormTv, mLocationTv);
    }

    private View mTarget;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_filter_from:
            case R.id.act_filter_location:
                initFilterLayout(v);
                break;
        }
    }

    @Override
    public void initFilterLayout(View target) {
        if (mTarget == null) {
            mTarget = target;
            //open
            mFilterContainer.setVisibility(View.VISIBLE);
            show(mTarget);
        } else if (mTarget.equals(target)) {
            //close
            mFilterContainer.setVisibility(View.GONE);
            mTarget = null;
        } else {
            //隐藏原来的，显示新的
            mTarget = target;
            show(mTarget);
        }
    }

    private void show(View target) {
        switch (target.getId()) {
            case R.id.act_filter_from:
                showFromLayout();
                break;
            case R.id.act_filter_location:
                showLocationLayout();
                break;
        }
    }

    private void showFromLayout() {
        if (mFromLayout == null) {
            mFromLayout = mFromViewStub.inflate();
        }
        showWitch(mFromLayout);
    }

    private void showWitch(View mFromLayout) {
        int childCount = mFilterContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mFilterContainer.getChildAt(i);
            if (childAt.equals(mFromLayout)) {
                childAt.setVisibility(View.VISIBLE);
            } else {
                childAt.setVisibility(View.GONE);
            }
        }
        mFilterContainer.requestLayout();
    }

    private void showLocationLayout() {
        if (mLocationLayout == null) {
            mLocationLayout = mLocationViewStub.inflate();
        }
        showWitch(mLocationLayout);
    }

    private List<BeanA> get() {
        List<BeanA> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new BeanA("item+" + i));
        }
        return data;
    }

    private final class BeanDelegate extends HolderDelegate<BeanA> {

        @Override
        public int getLayoutId() {
            return R.layout.item_a;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_a_tv);
            ImageView img = (ImageView) holder.findView(R.id.item_a_img);
            tv.setText(beanA.name);
            img.setImageResource(R.mipmap.ic_clock);
        }
    }
}

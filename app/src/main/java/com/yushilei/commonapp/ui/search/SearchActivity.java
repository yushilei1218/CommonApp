package com.yushilei.commonapp.ui.search;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class SearchActivity extends BaseActivity {

    private PtrFrameLayout mPtr;
    private LinearLayout mTabLayout;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private CoordinatorLayout mCoordinatorLayout;
    private View mBottomView;
    private View mContentV;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    private String getState(int state) {
        String a = "";
        switch (state) {
            case BottomSheetBehavior.STATE_COLLAPSED:
                a = "STATE_COLLAPSED";
                break;
            case BottomSheetBehavior.STATE_DRAGGING:
                a = "STATE_DRAGGING";
                break;
            case BottomSheetBehavior.STATE_EXPANDED:
                a = "STATE_EXPANDED";
                break;
            case BottomSheetBehavior.STATE_HIDDEN:
                a = "STATE_HIDDEN";
                break;
            case BottomSheetBehavior.STATE_SETTLING:
                a = "STATE_SETTLING";
                break;
        }
        return a;
    }

    @Override
    public void initView() {


        RecyclerView recyclerView = findView(R.id.act_search_recycler);
        mTabLayout = findView(R.id.act_search_tab);
        mTabLayout.requestFocus();
        mPtr = findView(R.id.act_search_ptr);
        mBottomView = findView(R.id.act_search_bottom);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomView);

        mCoordinatorLayout = findView(R.id.act_search_coordinator);
        mContentV = findView(R.id.act_search_content);
        mFloatingActionButton = findView(R.id.act_search_float_btn);

        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.setHeaderView(header);
        mPtr.addPtrUIHandler(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return frame.getY() >= mTabLayout.getHeight() && super.checkCanDoRefresh(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtr.refreshComplete();
                    }
                }, 2000);
            }
        });
        MultiHolderAdapter adapter = new MultiHolderAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setMatch(BeanB.class, new BeanDelegate());
        adapter.addAll(getList());

        setOnClick(R.id.act_search_float_btn
                , R.id.act_search_filter_job
                , R.id.act_search_filter_salary
                , R.id.act_search_filter_location
                , R.id.act_search_filter_other
        );
    }

    private List<BeanB> getList() {
        List<BeanB> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new BeanB(i));
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id != R.id.act_search_float_btn && mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        }
        switch (id) {
            case R.id.act_search_float_btn:
                int state = mBottomSheetBehavior.getState();
                Log.d(getTAG(), "onClick " + getState(state));
                if (state == BottomSheetBehavior.STATE_EXPANDED || state == BottomSheetBehavior.STATE_SETTLING) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.act_search_filter_job:
                if (mContentV.getVisibility()==View.VISIBLE){
                    mContentV.setVisibility(View.GONE);
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }else {
                    mContentV.setVisibility(View.VISIBLE);
                    mFloatingActionButton.setVisibility(View.GONE);

                }
                showToast("Job");
                break;
            case R.id.act_search_filter_salary:
                showToast("Salary");
                break;
            case R.id.act_search_filter_location:
                showToast("Location");
                break;
            case R.id.act_search_filter_other:
                showToast("Other");
                break;

        }
    }

    private final class BeanDelegate extends HolderDelegate<BeanB> {

        @Override
        public int getLayoutId() {
            return R.layout.item_b;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanB beanB, int pos) {
            TextView tv = (TextView) holder.findView(R.id.item_b_tv);
            String text = beanB.age + " 岁";
            tv.setText(text);
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<BeanB> holder, BeanB beanB) {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                return;
            }
            showToast(beanB.age + "");
        }
    }
}

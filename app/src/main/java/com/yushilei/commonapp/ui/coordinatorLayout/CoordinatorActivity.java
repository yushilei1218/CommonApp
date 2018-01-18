package com.yushilei.commonapp.ui.coordinatorLayout;


import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.R;

import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.base.Focus;
import com.yushilei.commonapp.common.bean.base.IFocus;
import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.item.BannerWrapper;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.common.widget.BannerIndicatorView;
import com.yushilei.commonapp.common.widget.BannerView;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class CoordinatorActivity extends BaseActivity {

    private ContentFragment mFragment;
    private BannerView mBanner;
    private AppBarLayout mAppBar;
    private View navigateView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinator;
    }

    @Override
    public void initView() {
        mFragment = (ContentFragment) getSupportFragmentManager().findFragmentById(R.id.act_coordinator_fg);

        final View view = mFragment.findView(R.id.fg_content_recycler);
        View job = mFragment.findView(R.id.act_coordinator_job);

        navigateView = findView(R.id.act_coordinator_navigate);

        mAppBar = findView(R.id.act_coordinator_app_bar);

        mBanner = findView(R.id.act_coordinator_banner);

        BannerIndicatorView indicatorView = findView(R.id.act_coordinator_indicator);

        mBanner.addOnPageChangeListener(indicatorView);
        mBanner.setCountChangeListener(indicatorView);

        initBanner();

        PtrFrameLayout mPtr = findView(R.id.act_coordinator_ptr);
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.addPtrUIHandler(header);
        mPtr.setHeaderView(header);

        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return mAppBar.getTop() == 0 && super.checkCanDoRefresh(frame, view, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 1000);
            }
        });
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getHeight() + verticalOffset == 0) {
                    navigateView.setVisibility(View.GONE);
                }else {
                    navigateView.setVisibility(View.VISIBLE);
                }
                Log.d(getTAG(), "top=" + appBarLayout.getTop() + " verticalOffset=" + verticalOffset);
            }
        });
        setOnClick(R.id.act_coordinator_a);
        setOnClick(R.id.act_coordinator_b);
        setOnClick(R.id.act_coordinator_c);
        setOnClick(job);
    }

    private void initBanner() {
        BannerView.Adapter adapter = mBanner.getAdapter();


        List<Focus> focuses = new ArrayList<>();
        Focus a = new Focus();

        Focus b = new Focus();
        a.setCover("http://img01.zhaopin.cn/IHRNB/mhr/imgs/generalize/Group3.png");
        b.setCover("http://img01.zhaopin.cn/IHRNB/mhr/imgs/generalize/Group3.png");
        focuses.add(a);
        focuses.add(b);
        List<BannerView.BannerWrapper> list = new LinkedList<>();
        for (Focus f : focuses) {
            list.add(new BannerWrapper.FocusWrapper(f));
        }
        adapter.addDataAndLoop(list);

    }

    boolean isShow = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_coordinator_job:
                mAppBar.setExpanded(false, false);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        MenuPopWindow mop = new MenuPopWindow(CoordinatorActivity.this);
                        mop.showAsDropDown(findViewById(R.id.act_coordinator_job));
                    }
                });
                break;
            case R.id.act_coordinator_a:
                mFragment.onError(0, "测试A", "点一下", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragment.hideLoading();
                        showToast("测试A");
                    }
                });
                break;
            case R.id.act_coordinator_b:
                if (isShow) {
                    mFragment.hideLoading();
                } else {
                    mFragment.showLoading();
                }
                isShow = !isShow;
                break;
            case R.id.act_coordinator_c:
                break;
            default:
                break;
        }
    }
}

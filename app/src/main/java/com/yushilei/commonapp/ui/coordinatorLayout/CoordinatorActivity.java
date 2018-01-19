package com.yushilei.commonapp.ui.coordinatorLayout;


import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;

import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.base.Focus;
import com.yushilei.commonapp.common.bean.base.IFocus;
import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.item.BannerWrapper;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.common.widget.BannerIndicatorView;
import com.yushilei.commonapp.common.widget.BannerView;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;
import com.yushilei.commonapp.ui.mvp.view.MvpActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


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

        GridView gird = findView(R.id.act_coordinator_grid);

        MultiListAdapter adapter = new MultiListAdapter(1);
        adapter.setMatch(Channel.class, new ChannelDelegate());
        gird.setAdapter(adapter);

        initTips(adapter);

        BannerIndicatorView indicatorView = findView(R.id.act_coordinator_indicator);

        mBanner.addOnPageChangeListener(indicatorView);
        mBanner.setCountChangeListener(indicatorView);

        initBanner();

        PtrFrameLayout mPtr = findView(R.id.act_coordinator_ptr);

        PtrFirstHeader header = new PtrFirstHeader(this) {
            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
                float currentPercent = ptrIndicator.getCurrentPercent();
                if (currentPercent >= 1f) {
                    navigateView.setAlpha(0f);
                }
                navigateView.setAlpha(1 - currentPercent);
                Log.d(getTAG(), "currentPercent=" + currentPercent);
            }
        };
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
                } else {
                    navigateView.setVisibility(View.VISIBLE);
                }
                Log.d(getTAG(), "top=" + appBarLayout.getTop() + " verticalOffset=" + verticalOffset);
            }
        });
        setOnClick(R.id.act_coordinator_a);
        setOnClick(R.id.act_coordinator_b);
        setOnClick(R.id.act_coordinator_c);
        setOnClick(job);
        setOnClick(navigateView);
    }

    private void initTips(MultiListAdapter adapter) {
        List<Channel> channelList = new ArrayList<>();
        channelList.add(new Channel("中国", "天安门", "http://img2.imgtn.bdimg.com/it/u=2850936076,2080165544&fm=206&gp=0.jpg"));
        channelList.add(new Channel("美国", "白宫", "http://img3.imgtn.bdimg.com/it/u=524208507,12616758&fm=206&gp=0.jpg"));
        channelList.add(new Channel("英国", "伦敦塔桥", "http://img3.imgtn.bdimg.com/it/u=698582197,4250615262&fm=206&gp=0.jpg"));
        channelList.add(new Channel("德国", "城堡", "http://img5.imgtn.bdimg.com/it/u=1467751238,3257336851&fm=11&gp=0.jpg"));
        channelList.add(new Channel("西班牙", "巴塞罗那", "http://img5.imgtn.bdimg.com/it/u=3191365283,111438732&fm=21&gp=0.jpg"));
        channelList.add(new Channel("意大利", "比萨斜塔", "http://img5.imgtn.bdimg.com/it/u=482494496,1350922497&fm=206&gp=0.jpg"));
        adapter.addAll(channelList);
    }

    private void initBanner() {
        BannerView.Adapter adapter = mBanner.getAdapter();


        List<Focus> focuses = new ArrayList<>();
        Focus a1 = new Focus();
        Focus a2 = new Focus();
        Focus a3 = new Focus();
        Focus a4 = new Focus();
        a1.setCover("http://img2.imgtn.bdimg.com/it/u=2850936076,2080165544&fm=206&gp=0.jpg");
        a2.setCover("http://img3.imgtn.bdimg.com/it/u=524208507,12616758&fm=206&gp=0.jpg");
        a3.setCover("http://img3.imgtn.bdimg.com/it/u=698582197,4250615262&fm=206&gp=0.jpg");
        a4.setCover("http://img5.imgtn.bdimg.com/it/u=1467751238,3257336851&fm=11&gp=0.jpg");

        focuses.add(a1);
        focuses.add(a2);
        focuses.add(a3);
        focuses.add(a4);

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
            case R.id.act_coordinator_navigate:
                startActivity(new Intent(this, MvpActivity.class));
                break;
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

    public final class ChannelDelegate extends HolderDelegate<Channel> {

        @Override
        public int getLayoutId() {
            return R.layout.item_channel;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, Channel channel, int pos) {
            SimpleDraweeView sdv = (SimpleDraweeView) holder.findView(R.id.item_channel_img);
            TextView tv = (TextView) holder.findView(R.id.item_channel_tv);
            sdv.setImageURI(channel.getImage_url());
            tv.setText(channel.getTitle());
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<Channel> holder, Channel channel) {
            showToast(channel.getTitle());
        }
    }
}

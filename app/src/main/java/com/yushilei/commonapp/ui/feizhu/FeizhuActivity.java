package com.yushilei.commonapp.ui.feizhu;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.loadmore2.Load2RecyclerView;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;
import com.yushilei.commonapp.ui.feizhu.bean.HotelBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author shilei.yu
 */
public class FeizhuActivity extends MvpBaseActivity<FeizhuConstract.IPresenter> implements FeizhuConstract.IView {

    @BindView(R.id.act_feizhu_lv)
    Load2RecyclerView mRecycler;
    @BindView(R.id.act_feizhu_ptr)
    PtrFrameLayout mPtr;
    @BindView(R.id.act_feizhu_header)
    View mHeader;

    @Override
    public FeizhuConstract.IPresenter getPresenter() {
        return new FeizhuPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feizhu;
    }

    @Override
    public void initView() {
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.setHeaderView(header);
        mPtr.addPtrUIHandler(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtr.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return frame.getY() >= mHeader.getHeight() && super.checkCanDoRefresh(frame, content, header);
            }
        });
        MultiHolderAdapter adapter = new MultiHolderAdapter();
        adapter.setMatch(HotelBean.class, new HotelDelegate());
        adapter.addAll(getData());
        mRecycler.setAdapter(adapter);

    }

    private List<HotelBean> getData() {
        List<HotelBean> data = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            data.add(new HotelBean("北京饭店 ++" + i));
        }
        return data;
    }

    private final class HotelDelegate extends HolderDelegate<HotelBean> {
        @Override
        public int getLayoutId() {
            return R.layout.item_hotel;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, HotelBean hotelBean, int pos) {
            ((SimpleDraweeView) holder.findView(R.id.item_hotel_img)).setImageResource(hotelBean.img);
            ((TextView) holder.findView(R.id.item_hotel_title)).setText(hotelBean.name);
        }
    }
}

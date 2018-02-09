package com.yushilei.commonapp.ui.feizhu;


import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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
import com.yushilei.commonapp.ui.feizhu.bean.HotelWrap;
import com.yushilei.commonapp.ui.feizhu.bean.LoadState;
import com.yushilei.commonapp.ui.feizhu.bean.SortBean;
import com.yushilei.commonapp.ui.feizhu.widget.BaseFilter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.act_feizhu_sort_btn)
    TextView mSortBtn;
    @BindView(R.id.act_feizhu_star_btn2)
    TextView mSortBtn2;
    @BindView(R.id.act_feizhu_filter_layout)
    LinearLayout mFilterLayout;
    @BindView(R.id.act_feizhu_location_btn3)
    TextView mLocationBtn3;
    @BindView(R.id.act_feizhu_drawer)
    DrawerLayout mDrawerLayout;

    private BaseFilter mBaseFilter;

    private MultiHolderAdapter mAdapter;


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
                presenter.load(LoadState.LOAD_REFRESH, true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return frame.getY() >= mHeader.getHeight() && super.checkCanDoRefresh(frame, content, header);
            }
        });
        mAdapter = new MultiHolderAdapter();
        mAdapter.setMatch(HotelBean.class, new HotelDelegate());

        mRecycler.setLoadMoreListener(new Load2RecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.load(LoadState.LOAD_MORE, false);
            }
        });

        mRecycler.setAdapter(mAdapter);

        mBaseFilter = new BaseFilter(mFilterLayout) {
            @Override
            public void onSortClicked(SortBean sortBean) {
                presenter.load(LoadState.LOAD_DIALOG, true);
            }
        };
    }

    @OnClick({R.id.act_feizhu_sort_btn, R.id.act_feizhu_star_btn2, R.id.act_feizhu_location_btn3, R.id.act_feizhu_common_btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_feizhu_sort_btn:
                presenter.onSortBtnClick();
                break;
            case R.id.act_feizhu_star_btn2:
                presenter.onStarBtnClick();
                break;
            case R.id.act_feizhu_location_btn3:
                presenter.onLocationBtnClick();
                break;
            case R.id.act_feizhu_common_btn4:
                presenter.onCommonBtnClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void showSortFilterView(List<SortBean> data) {
        mBaseFilter.showSortFilterView(data);
    }

    @Override
    public void showStarFilterView() {
        mBaseFilter.showStarFilterView();
    }

    @Override
    public void showLocationFilterView() {
        mBaseFilter.showLocationFilterView();
    }

    @Override
    public void openDrawer() {
        hideFilterLayout();
        mDrawerLayout.openDrawer(Gravity.END);
    }

    @Override
    public void hideFilterLayout() {
        mBaseFilter.hideFilterLayout();
    }

    @Override
    public void notifyDataChanged(HotelWrap wrap) {
        mAdapter.replaceData(wrap.data);
    }

    @Override
    public void changeLoadSate(boolean isShow, LoadState state) {

        switch (state) {
            case LOAD_DIALOG:
                if (isShow) {
                    showLoadingDialog();
                } else {
                    hideLoadingDialog();
                }
                break;
            case LOAD_INNER:
            case LOAD_RETRY:
                if (isShow) {
                    showLoading();
                } else {
                    hideLoading();
                }
                break;
            case LOAD_REFRESH:
                if (!isShow) {
                    mPtr.refreshComplete();
                }
                break;
            case LOAD_MORE:
                if (!isShow) {
                    mRecycler.loadFinish();
                }
                break;
            default:
                break;
        }

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

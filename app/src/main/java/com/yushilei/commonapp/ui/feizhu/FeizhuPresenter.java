package com.yushilei.commonapp.ui.feizhu;

import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.ui.feizhu.bean.HotelWrap;
import com.yushilei.commonapp.ui.feizhu.bean.LoadState;
import com.yushilei.commonapp.ui.feizhu.bean.SortBean;
import com.yushilei.commonapp.ui.feizhu.callback.CallBack;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public class FeizhuPresenter extends BasePresenter<FeizhuConstract.IView> implements FeizhuConstract.IPresenter {
    private FeizhuConstract.IModel model;

    public FeizhuPresenter(FeizhuConstract.IView view) {
        super(view);
        model = new FeizhuModel();
    }

    @Override
    public void onStart() {
        //文案绑定

    }

    @Override
    public void onSortBtnClick() {
        List<SortBean> sorts = model.getSorts();
        mView.showSortFilterView(sorts);
    }

    @Override
    public void onSortItemClicked(SortBean bean) {

    }

    @Override
    public void onStarBtnClick() {
        mView.showStarFilterView();
    }

    @Override
    public void onLocationBtnClick() {
        mView.showLocationFilterView();
    }

    @Override
    public void onCommonBtnClick() {
        mView.openDrawer();
    }

    @Override
    public void load(final LoadState state, boolean isRefresh) {
        mView.changeLoadSate(true, state);

        model.load(isRefresh, new CallBack<HotelWrap>() {
            @Override
            public void callBack(HotelWrap data) {
                mView.changeLoadSate(false, state);
                mView.notifyDataChanged(data);
            }

            @Override
            public void onFailed(String msg, int code) {
                if (mView != null) {
                    mView.changeLoadSate(false, state);
                }
            }
        });
    }
}

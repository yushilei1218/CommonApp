package com.yushilei.commonapp.ui.feizhu;

import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;
import com.yushilei.commonapp.ui.feizhu.bean.HotelWrap;
import com.yushilei.commonapp.ui.feizhu.bean.LoadState;
import com.yushilei.commonapp.ui.feizhu.bean.SortBean;
import com.yushilei.commonapp.ui.feizhu.callback.CallBack;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public interface FeizhuConstract {
    interface IPresenter extends IBasePresenter {
        void onSortBtnClick();

        void onSortItemClicked(SortBean bean);

        void onStarBtnClick();

        void onLocationBtnClick();

        void onCommonBtnClick();

        void load(LoadState state, boolean isRefresh);
    }

    interface IView extends IBaseView, IFilter {
        void openDrawer();

        void changeLoadSate(boolean isShow, LoadState state);

        void notifyDataChanged(HotelWrap wrap);
    }

    interface IFilter {
        void showSortFilterView(List<SortBean> data);

        void showStarFilterView();

        void showLocationFilterView();

        void hideFilterLayout();
    }

    interface IModel {
        List<SortBean> getSorts();

        void load(boolean isRefresh, CallBack<HotelWrap> callBack);
    }
}

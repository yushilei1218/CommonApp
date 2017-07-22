package com.yushilei.commonapp.ui.mvp.contract;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class HomeContract {
    public interface IView extends IBaseView {
        void onRefreshing();

        void onRefreshFinish(List<ItemWrapper> data);

    }

    public interface Presenter extends IBasePresenter {
        void beginRefreshData(boolean isRefresh);
    }

    public interface IModel {
        List<ItemWrapper> obtainItems(Recommend recommend);
    }
}

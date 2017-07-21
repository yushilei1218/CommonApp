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

    }

    public interface Presenter extends IBasePresenter {
        void refreshData();
    }

    public interface IModel {
        List<ItemWrapper> obtainItems(Recommend recommend);
    }
}

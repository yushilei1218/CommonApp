package com.yushilei.commonapp.ui.mvp.contract;

import android.support.annotation.Nullable;

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

        /**
         * 刷新完毕
         *
         * @param isByRefresh  是否是用户手动刷新
         * @param isNetSuccess 网络请求是否成功
         * @param data         请求数据 可能为null
         */
        void onRefreshFinish(boolean isByRefresh, boolean isNetSuccess, @Nullable List<ItemWrapper> data);

    }

    public interface Presenter extends IBasePresenter {
        /**
         * 刷新数据
         *
         * @param isRefresh true：用户触发刷新， false App触发刷新
         */
        void beginRefreshData(boolean isRefresh);

        void beginLoadMore();
    }

    public interface IModel {
        List<ItemWrapper> obtainItems(Recommend recommend);
    }
}

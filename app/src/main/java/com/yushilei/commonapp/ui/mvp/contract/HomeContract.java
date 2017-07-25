package com.yushilei.commonapp.ui.mvp.contract;

import android.support.annotation.Nullable;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.Discovery;
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
        /**
         * 进入刷新状态
         */
        void onRefreshing();

        /**
         * 刷新完毕
         *
         * @param isByRefresh  是否是用户手动刷新
         * @param isNetSuccess 网络请求是否成功
         * @param data         请求数据 可能为null
         */
        void onRefreshFinish(boolean isByRefresh, boolean isNetSuccess, @Nullable List<ItemWrapper> data);

        /**
         * 加载更多完毕
         *
         * @param isNetSuccess true:网络请求成功，false:网络异常
         * @param data         加载更多的数据，可能为null
         */
        void onLoadMoreFinish(boolean isNetSuccess, @Nullable List<ItemWrapper> data);

        void onCancelLoadMore();
    }

    public interface Presenter extends IBasePresenter {
        /**
         * 开始进行刷新数据
         *
         * @param isRefresh true：用户触发刷新， false App触发刷新
         */
        void beginRefreshData(boolean isRefresh);

        /**
         * 开始进行加载更多
         */
        void beginLoadMore();
    }

    public interface IModel {
        List<ItemWrapper> obtainItems(Discovery discovery);

        List<ItemWrapper> obtainAlbums(Recommend recommend);

        int getPageId();

        int getPageSize();
    }
}

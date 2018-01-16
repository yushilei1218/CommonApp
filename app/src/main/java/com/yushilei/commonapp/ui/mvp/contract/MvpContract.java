package com.yushilei.commonapp.ui.mvp.contract;

import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;
import com.yushilei.commonapp.ui.mvp.bean.LoadMode;
import com.yushilei.commonapp.ui.mvp.callback.ICallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public class MvpContract {
    public interface IView extends IBaseView {

        void bindData(List<Data> data);

        void notifyDataChanged(boolean hasMore);

        void changeLoadState(LoadMode mode, boolean isShow);
    }

    public interface IPresenter extends IBasePresenter {
        void init();

        void refresh(LoadMode mode);

        void loadMore();
    }

    public static abstract class BaseModel {
        private final int taskId;

        public BaseModel(int taskId) {
            this.taskId = taskId;
        }

        public final List<Data> data = new ArrayList<>();

        public abstract void refresh(ICallBack<List<Data>> callBack);

        public abstract void loadMore(ICallBack<List<Data>> callBack);

    }
}

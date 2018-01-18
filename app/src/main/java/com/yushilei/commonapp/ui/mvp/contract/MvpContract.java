package com.yushilei.commonapp.ui.mvp.contract;

import com.yushilei.commonapp.common.bean.basedata.Job;
import com.yushilei.commonapp.common.bean.net.Album;
import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.bean.net.YouLike;
import com.yushilei.commonapp.common.mvp.IBasePresenter;
import com.yushilei.commonapp.common.mvp.IBaseView;
import com.yushilei.commonapp.ui.mvp.bean.LoadMode;
import com.yushilei.commonapp.ui.mvp.callback.BooleanCallBack;
import com.yushilei.commonapp.ui.mvp.callback.ICallBack;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public class MvpContract {
    public interface IView extends IBaseView {

        void bindData(List<Album> data);

        void notifyDataChanged(boolean hasMore);

        void changeLoadState(LoadMode mode, boolean isShow);
    }

    public interface IPresenter extends IBasePresenter {
        void init();

        void refresh(LoadMode mode);

        void loadMore();

        void refreshRx(LoadMode mode);

        void loadMoreRx();
    }

    public static abstract class BaseModel {
        protected final int taskId;
        public boolean isUserCheckPassed = false;

        public BaseModel() {
            this.taskId = this.hashCode();
        }

        public final List<Album> data = new ArrayList<>();

        public abstract void refresh(ICallBack<List<Album>> callBack);

        public abstract void loadMore(ICallBack<List<Album>> callBack);

        public abstract Observable<YouLike> refresh();

        public abstract Observable<YouLike> loadMore();

        public abstract void refreshRx(ICallBack<List<Album>> callBack);

        public abstract void refreshJobs(ICallBack<List<Job>> callBack);

        public abstract void loadMoreJobs(ICallBack<List<Job>> callBack);

        public abstract void checkUserState(BooleanCallBack callBack);

    }
}

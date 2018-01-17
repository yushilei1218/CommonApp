package com.yushilei.commonapp.ui.mvp.presenter;

import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.common.bean.net.Album;
import com.yushilei.commonapp.common.bean.net.YouLike;
import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.net.callback.BaseObserver;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.ui.mvp.bean.LoadMode;
import com.yushilei.commonapp.ui.mvp.callback.ICallBack;
import com.yushilei.commonapp.ui.mvp.contract.MvpContract;
import com.yushilei.commonapp.ui.mvp.model.MvpModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 1检测用户状态->  通过拉取职位 -> 成功拉取 简历
 *
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public class MvpPresenter extends BasePresenter<MvpContract.IView> implements MvpContract.IPresenter {
    private final MvpContract.BaseModel mModel;

    public MvpPresenter(MvpContract.IView view) {
        super(view);
        mModel = new MvpModel(mTaskId);
    }

    @Override
    public void init() {
        mView.bindData(mModel.data);
    }

    @Override
    public void refresh(final LoadMode mode) {
        mView.changeLoadState(mode, true);

        mModel.refresh(new ICallBack<List<Album>>() {
            @Override
            public void onSuccess(List<Album> data) {
                mView.changeLoadState(mode, false);
                //1.成功-首页有数据 2.成功-首页无数据 3失败-首页有数据 4失败-首页无数据

                mView.notifyDataChanged(true);

                if (SetUtil.isEmpty(data)) {
                    mView.onError(0, "首页无数据", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refresh(LoadMode.COM_LOAD);
                        }
                    });
                }
            }

            @Override
            public void onFail(String msg) {
                if (mView != null) {
                    mView.changeLoadState(mode, false);
                    mView.onError(0, "网络异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refresh(LoadMode.COM_LOAD);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void loadMore() {
        mModel.loadMore(new ICallBack<List<Album>>() {
            @Override
            public void onSuccess(List<Album> data) {
                //1.有更多数据 2 无更多数据
                mView.changeLoadState(LoadMode.LOAD_MORE_FINISH, false);
                mView.notifyDataChanged(true);
            }

            @Override
            public void onFail(String msg) {
                //失败 关闭loadmore
                mView.changeLoadState(LoadMode.LOAD_MORE_FINISH, false);
                mView.showToast(msg);
            }
        });
    }

    @Override
    public void refreshRx(final LoadMode mode) {
        mModel.refresh()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<YouLike>(mTaskId) {
                    @Override
                    public void onSuccess(@NonNull YouLike youLike) {
                        Log.d("refreshRx", "youLike");
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        Log.d("refreshRx", code + " " + msg);
                    }
                });
    }

    @Override
    public void loadMoreRx() {

    }
}

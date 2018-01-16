package com.yushilei.commonapp.ui.mvp.presenter;

import android.view.View;

import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.mvp.BasePresenter;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.ui.mvp.bean.LoadMode;
import com.yushilei.commonapp.ui.mvp.callback.ICallBack;
import com.yushilei.commonapp.ui.mvp.contract.MvpContract;
import com.yushilei.commonapp.ui.mvp.model.MvpModel;

import java.util.List;

/**
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

        mModel.refresh(new ICallBack<List<Data>>() {
            @Override
            public void onSuccess(List<Data> data) {
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
        mModel.loadMore(new ICallBack<List<Data>>() {
            @Override
            public void onSuccess(List<Data> data) {
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
}

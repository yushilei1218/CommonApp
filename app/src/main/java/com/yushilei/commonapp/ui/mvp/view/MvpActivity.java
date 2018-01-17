package com.yushilei.commonapp.ui.mvp.view;



import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.bean.net.Album;

import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.common.widget.LoadListView;
import com.yushilei.commonapp.common.widget.PtrFirstHeader;
import com.yushilei.commonapp.ui.mvp.bean.LoadMode;
import com.yushilei.commonapp.ui.mvp.contract.MvpContract;
import com.yushilei.commonapp.ui.mvp.presenter.MvpPresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MvpActivity extends MvpBaseActivity<MvpContract.IPresenter> implements MvpContract.IView {

    private PtrFrameLayout mPtr;
    private MultiListAdapter mAdapter;
    private LoadListView mLv;

    @Override
    public MvpContract.IPresenter getPresenter() {
        return new MvpPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void initView() {
        mLv = findView(R.id.act_mvp_lv);
        mPtr = findView(R.id.act_mvp_ptr);
        PtrFirstHeader header = new PtrFirstHeader(this);
        mPtr.setHeaderView(header);
        mPtr.addPtrUIHandler(header);
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refresh(LoadMode.PTR_LOAD);
            }
        });
        mLv.setLoadMoreListener(new LoadListView.OnLoadMoreListener() {
            @Override
            public void onLoadingMore() {
                presenter.loadMore();
            }
        });
        mAdapter = new MultiListAdapter(1);
        mAdapter.setMatch(Album.class, new AlbumDelegate());
        mLv.setAdapter(mAdapter);

        presenter.init();
    }

    @Override
    public void bindData(List<Album> data) {
        mAdapter.setRootData(data);
    }

    @Override
    public void notifyDataChanged(boolean is) {
        mAdapter.notifyDataSetChanged();
        if (is) {
            mLv.loadFinish();
        } else {
            mLv.noMore();
        }
    }

    @Override
    public void changeLoadState(LoadMode mode, boolean isShow) {
        switch (mode) {
            case COM_LOAD:
                if (isShow) {
                    showLoading();
                } else {
                    hideLoading();
                }
                break;
            case DIALOG_LOAD:
                if (isShow) {
                    showLoadingDialog();
                } else {
                    hideLoadingDialog();
                }
                break;
            case PTR_LOAD:
                if (!isShow) {
                    mPtr.refreshComplete();
                }
                break;
            case LOAD_MORE_FINISH:
                mLv.loadFinish();
                break;
            case LOAD_MODE_NO_MORE:
                mLv.noMore();
                break;
            default:
                break;
        }
    }

    public final class AlbumDelegate extends HolderDelegate<Album> {

        @Override
        public int getLayoutId() {
            return R.layout.item_album2;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, Album album, int pos) {
            SimpleDraweeView sdv = (SimpleDraweeView) holder.findView(R.id.item_album_img2);
            TextView tv = (TextView) holder.findView(R.id.item_album_title);
            sdv.setImageURI(album.getCoverMiddle());
            tv.setText(album.getTitle());
        }
    }
}

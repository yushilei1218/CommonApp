package com.yushilei.commonapp.common.mvp;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class OperateViewHolder implements IOperateView {
    private View mViewLayout;
    /**
     * View层是否引入的 指定布局
     */
    private boolean hasView = false;

    private View mProcessBar;

    private View mEmptyLayout;

    private TextView mErrTv;

    private ImageView mEmptyImg;

    private final Activity activityContext;

    private AlertDialog mLoadingDialog;

    public OperateViewHolder(IBaseView baseView) {
        View view = baseView.findView(R.id.error_layout);
        activityContext = baseView.getActivityContext();
        hasView = view != null;
        if (hasView) {
            mViewLayout = view;
            mProcessBar = view.findViewById(R.id.err_process);
            mEmptyLayout = view.findViewById(R.id.err_empty_layout);
            mErrTv = (TextView) view.findViewById(R.id.err_tv);
            mEmptyImg = (ImageView) view.findViewById(R.id.err_empty_img);
        }
    }

    @Override
    public void initOperateView() {
        if (hasView) {
            mViewLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if (hasView) {
            mViewLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.GONE);
            mProcessBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (hasView) {
            mViewLayout.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
            mProcessBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AlertDialog.Builder(activityContext,R.style.request_loading)
                    .setView(R.layout.item_dialog_loading)
                    .create();

            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setCanceledOnTouchOutside(false);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError(int imageId, String msg, String btnText, View.OnClickListener onClickListener) {
        if (hasView) {
            mViewLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.VISIBLE);
            if (imageId != 0) {
                mEmptyImg.setImageResource(imageId);
            }
            mErrTv.setText(msg);
            mErrTv.setOnClickListener(onClickListener);
            mProcessBar.setVisibility(View.GONE);
        }
    }
}

package com.yushilei.commonapp.common.mvp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class ErrorViewHolder implements IErrorView {
    private View mViewLayout;
    private boolean hasView = false;
    private View mProcessBar;
    private View mEmptyLayout;
    private TextView mErrTv;
    private ImageView mEmptyImg;

    public ErrorViewHolder(IBaseView baseView) {
        View view = baseView.findView(R.id.error_layout);
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
    public void initErrorView() {
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
    public void onError(int imageId, String msg, String btnText, View.OnClickListener onClickListener) {
        if (hasView) {
            mViewLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.VISIBLE);
            mEmptyImg.setImageResource(imageId);
            mErrTv.setText(msg);
            mErrTv.setOnClickListener(onClickListener);
            mProcessBar.setVisibility(View.GONE);
        }
    }
}

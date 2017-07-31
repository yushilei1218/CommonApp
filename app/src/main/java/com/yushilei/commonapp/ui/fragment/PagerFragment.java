package com.yushilei.commonapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends BaseFragment {
    private static final String EXTRA_ = "EXTRA_";
    private String mTitle;
    private TextView mTv;

    public static PagerFragment instance(String title) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void parseDataByArguments(Bundle arguments) {
        mTitle = arguments.getString(EXTRA_);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager;
    }

    @Override
    public void initView() {
        mTv = findView(R.id.fg_pager_tv);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        Log.i(getTAG(), "lazyLoad+" + mTitle);
        mTv.setText(mTitle);
    }

    @Override
    public void onDestroy() {
        Log.i(getTAG(), "onDestroy" + " " + this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(getTAG(), "onDestroyView" + " " + this);
        super.onDestroyView();
    }
}

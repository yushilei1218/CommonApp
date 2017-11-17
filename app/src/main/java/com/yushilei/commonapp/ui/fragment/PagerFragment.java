package com.yushilei.commonapp.ui.fragment;


import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.util.Log;

import android.view.View;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseFragment;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.ui.sharedelement.DetailFragment;
import com.yushilei.commonapp.ui.sharedelement.DetailsTransition;

import java.util.ArrayList;

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
    protected void restoreDataBySavedBundle(Bundle savedInstanceState) {
        mTitle = savedInstanceState.getString(EXTRA_);
    }

    @Override
    protected void saveInstanceState(Bundle outState) {
        outState.putString(EXTRA_, mTitle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager;
    }

    @Override
    public void initView() {
        mTv = findView(R.id.fg_pager_tv);
        GridView grid = findView(R.id.grid);
        MultiListAdapter adapter = new MultiListAdapter(1);
        adapter.setMatch(BeanA.class, new Item());
        grid.setAdapter(adapter);
        ArrayList<BeanA> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new BeanA("" + i));
        }
        adapter.replaceData(data);
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

    public final class Item extends HolderDelegate<BeanA> {
        @Override
        public int getLayoutId() {
            return R.layout.item_grid_shared;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            ImageView img = (ImageView) holder.findView(R.id.img);
            img.setImageResource(R.mipmap.ic_clock);
            img.setOnClickListener(holder);
            ViewCompat.setTransitionName(img, pos + "_img");
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<BeanA> holder, BeanA beanA) {
            DetailFragment fg = new DetailFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fg.setSharedElementEnterTransition(new DetailsTransition());
                fg.setEnterTransition(new Fade());
                setExitTransition(new Fade());
                fg.setSharedElementReturnTransition(new DetailsTransition());
            }
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.addSharedElement(target, "kittenImage")
                    .add(R.id.container2, fg)
                    .addToBackStack(null)
                    .commit();
        }
    }
}

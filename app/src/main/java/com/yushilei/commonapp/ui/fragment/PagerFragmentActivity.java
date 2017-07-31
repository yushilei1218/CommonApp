package com.yushilei.commonapp.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 小结{@link FragmentStatePagerAdapter 会从onCreate -onCreateView -onDestroyView-onDestory}
 */
public class PagerFragmentActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pager_fragment;
    }

    @Override
    public void initView() {
        ViewPager mPager = findView(R.id.fg_pager);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("item+" + i);
        }
        mPager.setAdapter(new FgAdapter(getSupportFragmentManager(), data));
    }

    private final class FgAdapter extends FragmentStatePagerAdapter {
        List<String> titles;

        FgAdapter(FragmentManager fm, List<String> titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(getTAG(), "getItem position =" + position);
            return PagerFragment.instance(titles.get(position));
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }
}

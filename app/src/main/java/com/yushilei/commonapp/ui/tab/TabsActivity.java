package com.yushilei.commonapp.ui.tab;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.widget.TagRadioButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class TabsActivity extends BaseActivity {

    private ArrayList<TagFragment> mData;

    private Fragment mCurFragment;
    private RadioGroup mTabGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tabs;
    }

    @Subscribe
    public void onChangeTabEvent(ChangeTabEvent event) {
        Log.d(getTAG(), event.pos + "");
        if (event.pos >= 0 && event.pos < mData.size()) {
            switch (event.pos) {
                case 0:
                    mTabGroup.check(R.id.tab1);
                    break;
                case 1:
                    mTabGroup.check(R.id.tab2);
                    break;
                case 2:
                    mTabGroup.check(R.id.tab3);
                    break;
                case 3:
                    mTabGroup.check(R.id.tab4);
                    break;
            }
        }
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mTabGroup = findView(R.id.main_tab);

        mData = new ArrayList<>();
        mData.add(TagFragment.newInstance("TAB1"));
        mData.add(TagFragment.newInstance("TAB2"));
        mData.add(TagFragment.newInstance("TAB3"));
        mData.add(TagFragment.newInstance("TAB4"));

        TagRadioButton tab1 = findView(R.id.tab1);
        tab1.setShowTag(true);


        mTabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                isNeedPost = false;
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                int pos = 0;
                switch (checkedId) {
                    case R.id.tab1:
                        pos = 0;
                        break;
                    case R.id.tab2:
                        pos = 1;
                        break;
                    case R.id.tab3:
                        pos = 2;
                        break;
                    case R.id.tab4:
                        pos = 3;
                        break;
                }
                Log.d(getTAG(), "onCheckedChanged "+pos);
                TagFragment fragment = mData.get(pos);

                if (mCurFragment != null && mCurFragment.equals(fragment)) {
                    return;
                }
                if (mCurFragment == null) {
                    if (fragment.isAdded()) {
                        transaction.show(fragment);
                    } else {
                        transaction.add(R.id.act_fg_container, fragment);
                    }
                } else {
                    if (fragment.isAdded()) {
                        transaction.hide(mCurFragment).show(fragment);
                    } else {
                        transaction.hide(mCurFragment).add(R.id.act_fg_container, fragment);
                    }
                }
                mCurFragment = fragment;
                transaction.commit();
            }
        });
        setOnClick(R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4);

        mTabGroup.check(R.id.tab1);
    }

    private boolean isNeedPost = false;

    @Override
    public void onClick(View v) {
        Log.d(getTAG(), "onClick");
        if (!isNeedPost) {
            isNeedPost = true;
            return;
        }

        switch (v.getId()) {
            case R.id.tab1:
                EventBus.getDefault().post(new TabClickEvent(0));
                break;
            case R.id.tab2:
                EventBus.getDefault().post(new TabClickEvent(1));
                break;
            case R.id.tab3:
                EventBus.getDefault().post(new TabClickEvent(2));
                break;
            case R.id.tab4:
                EventBus.getDefault().post(new TabClickEvent(3));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

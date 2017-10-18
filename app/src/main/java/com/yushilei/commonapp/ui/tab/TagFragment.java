package com.yushilei.commonapp.ui.tab;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseFragment;
import com.yushilei.commonapp.common.bean.BeanA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends BaseFragment {

    public static final String TAG = "TagFragment";
    private String mTag;
    private ListView mLv;
    private MultiListAdapter mAdapter;

    public TagFragment() {
    }

    public static TagFragment newInstance(String text) {
        TagFragment fg = new TagFragment();
        Bundle args = new Bundle();
        args.putString(TAG, text);
        fg.setArguments(args);
        return fg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getArguments().getString(TagFragment.TAG);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.d(getTAG(),mTag+" onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(getTAG(),mTag+" onResume");
        super.onResume();
    }

    @Override
    protected void lazyLoad() {
        Log.d(getTAG(),mTag+" lazyLoad");
    }

    @Override
    protected void onActiveView() {
        Log.d(getTAG(),mTag+" onActiveView");
    }

    @Override
    protected void onSleepView() {
        Log.d(getTAG(),mTag+" onSleepView");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tag;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabClickEvent(TabClickEvent event) {
        if (isAdded() && isResumed()) {
            switch (mTag) {
                case "TAB1":
                    if (event.pos == 0) {
                        mLv.setSelection(0);
                        // mAdapter.notifyDataSetChanged();
                    }
                    break;
                case "TAB2":
                    if (event.pos == 1) {
                        mLv.scrollTo(0, 0);
                    }
                    break;
                case "TAB3":
                    if (event.pos == 2) {
                        mLv.scrollTo(0, 0);
                    }
                    break;
                case "TAB4":
                    if (event.pos == 3) {
                        mLv.scrollTo(0, 0);
                    }
                    break;
            }
        }
    }

    @Override
    public void initView() {
        TextView tv = findView(R.id.fg_tag_tv);
        mLv = findView(R.id.fg_tag_lv);
        tv.setText(mTag);
        mAdapter = new MultiListAdapter(1);
        mAdapter.setMatch(BeanA.class, new BeanDelegate());
        List<BeanA> data = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            data.add(new BeanA("你好+" + i));
        }
        mAdapter.addAll(data);
        mLv.setAdapter(mAdapter);
        setOnClick(R.id.fg_tag_tv);
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new ChangeTabEvent(new Random().nextInt(4)));
    }

    private final class BeanDelegate extends HolderDelegate<BeanA> {

        @Override
        public int getLayoutId() {
            return R.layout.item_a;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            TextView view = (TextView) holder.findView(R.id.item_a_tv);
            view.setText(beanA.name);

        }
    }
}

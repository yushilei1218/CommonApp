package com.yushilei.commonapp.ui.coordinatorLayout;


import android.support.v4.app.Fragment;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.base.BaseFragment;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.delegate.BeanADelegate;
import com.yushilei.commonapp.common.loadmore2.Load2RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends BaseFragment {


    public ContentFragment() {
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public void initView() {
        Load2RecyclerView recycler = findView(R.id.fg_content_recycler);
        MultiHolderAdapter adapter = new MultiHolderAdapter();
        adapter.setMatch(BeanA.class, new BeanADelegate());
        recycler.setAdapter(adapter);
        List<BeanA> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new BeanA(" item+" + i));
        }
        adapter.addAll(data);
    }
    
}

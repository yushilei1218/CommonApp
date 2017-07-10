package com.yushilei.commonapp.ui.multilv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.ui.multirecycler.MultiRecyclerActivity;

import java.util.LinkedList;
import java.util.List;

public class MultiListViewActivity extends BaseActivity {


    @Override
    protected void initView() {
        ListView mLv = findView(R.id.activity_multi_lv);
        MultiBaseAdapter adapter = new MultiBaseAdapter();
        mLv.setAdapter(adapter);
        BeanA a1 = new BeanA("yushilei");
        BeanA a2 = new BeanA("ceshi");
        BeanA a3 = new BeanA("just test");
        BeanB b1 = new BeanB(121);
        BeanB b2 = new BeanB(123);
        BeanB b3 = new BeanB(456);
        BeanB b4 = new BeanB(382);
        BeanB b5 = new BeanB(222);
        List<ItemWrapper> data = new LinkedList<>();
        data.add(new MultiRecyclerActivity.BaseItemA(a1));
        data.add(new MultiRecyclerActivity.BaseItemA(a2));
        data.add(new MultiRecyclerActivity.BaseItemB(b1));

        data.add(new MultiRecyclerActivity.BaseItemA(a3));

        data.add(new MultiRecyclerActivity.BaseItemB(b2));
        data.add(new MultiRecyclerActivity.BaseItemB(b3));
        data.add(new MultiRecyclerActivity.BaseItemB(b4));
        data.add(new MultiRecyclerActivity.BaseItemB(b5));
        data.add(new MultiRecyclerActivity.BaseItemB(b5));
        data.add(new MultiRecyclerActivity.BaseItemB(b5));
        data.add(new MultiRecyclerActivity.BaseItemB(b5));
        data.add(new MultiRecyclerActivity.BaseItemB(b5));
        data.add(new MultiRecyclerActivity.BaseItemB(b5));
        data.add(new MultiRecyclerActivity.BaseItemB(b5));

        adapter.addAll(data);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_list_view;
    }
}

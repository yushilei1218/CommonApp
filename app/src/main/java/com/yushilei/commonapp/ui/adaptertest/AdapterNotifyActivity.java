package com.yushilei.commonapp.ui.adaptertest;


import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotifyActivity extends BaseActivity {

    private MultiListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adapter_notify;
    }

    @Override
    public void initView() {
        View view = findView(R.id.act_notify_show_list);
        setOnClick(R.id.act_notify_show_list);
        ListView lv = (ListView) findView(R.id.act_notify_lv);
        adapter = new MultiListAdapter(1);
        lv.setAdapter(adapter);
        adapter.setMatch(BeanA.class, new BeanDelegate());


    }

    private int index;

    private List<BeanA> getList() {
        List<BeanA> data = new ArrayList<>();

        for (int i = index; i < index + 10; i++) {

            data.add(new BeanA("" + i));
        }
        index += 10;
        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_notify_show_list:
                adapter.setRootData(getList());
                break;
        }
    }

    private class BeanDelegate extends HolderDelegate<BeanA> {
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

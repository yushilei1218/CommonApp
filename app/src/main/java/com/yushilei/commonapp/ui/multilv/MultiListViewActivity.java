package com.yushilei.commonapp.ui.multilv;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.ui.multirecycler.MultiRecyclerActivity;

import java.util.LinkedList;
import java.util.List;

public class MultiListViewActivity extends BaseActivity {


    private MultiBaseAdapter adapter;

    @Override
    public void initView() {
        ListView mLv = findView(R.id.activity_multi_lv);
        adapter = new MultiBaseAdapter(2);
        mLv.setAdapter(adapter);
        //构造数据源
        List<ItemWrapper> data = getItemWrappers();

        adapter.addAll(data);
    }

    @NonNull
    private List<ItemWrapper> getItemWrappers() {
        List<ItemWrapper> data = new LinkedList<>();
        BeanA a1 = new BeanA("yushilei");
        BeanA a2 = new BeanA("ceshi");
        BeanA a3 = new BeanA("just test");
        BeanB b1 = new BeanB(121);
        BeanB b2 = new BeanB(123);
        BeanB b3 = new BeanB(456);
        BeanB b4 = new BeanB(382);
        BeanB b5 = new BeanB(222);

        data.add(new BaseItemA(a1));
        data.add(new BaseItemA(a2));
        data.add(new BaseItemB(b1));

        data.add(new BaseItemA(a3));

        data.add(new BaseItemB(b2));
        data.add(new BaseItemB(b3));
        data.add(new BaseItemB(b4));
        data.add(new BaseItemB(b5));
        data.add(new BaseItemB(b5));
        data.add(new BaseItemB(b5));
        data.add(new BaseItemB(b5));
        data.add(new BaseItemB(b5));
        data.add(new BaseItemB(b5));
        data.add(new BaseItemB(b5));
        return data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_list_view;
    }

    /*
        数据源封装
     */
    public class BaseItemB extends ItemWrapper<BeanB> implements View.OnClickListener {

        public BaseItemB(BeanB bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_b;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView view = holder.findView(R.id.item_b_tv);
            ImageView img = holder.findView(R.id.item_b_img);

            String text = bean.age + " 岁";
            view.setText(text);
            img.setImageResource(R.mipmap.ic_launcher);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapter.remove(this);
            Toast.makeText(v.getContext(), "Age=" + bean.age, Toast.LENGTH_SHORT).show();
        }
    }

    public class BaseItemA extends ItemWrapper<BeanA> implements View.OnClickListener {
        public BaseItemA(BeanA bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_a;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView view = holder.findView(R.id.item_a_tv);
            ImageView img = holder.findView(R.id.item_a_img);

            view.setText(bean.name);
            img.setImageResource(R.mipmap.ic_launcher_round);

            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapter.remove(this);
            Toast.makeText(v.getContext(), "Name=" + bean.name, Toast.LENGTH_SHORT).show();
        }
    }
}

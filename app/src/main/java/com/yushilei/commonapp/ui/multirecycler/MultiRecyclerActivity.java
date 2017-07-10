package com.yushilei.commonapp.ui.multirecycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;

import java.util.LinkedList;
import java.util.List;

public class MultiRecyclerActivity extends BaseActivity {

    @Override
    protected void initView() {
        RecyclerView mRecycler = findView(R.id.activity_multi_recycler);
        MultiRecyclerAdapter adapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(adapter);

        List<ItemWrapper> mData = getItemWrappers();

        adapter.addAll(mData);

    }

    @NonNull
    private List<ItemWrapper> getItemWrappers() {
        List<ItemWrapper> mData = new LinkedList<>();
        BeanA a1 = new BeanA("yushilei");
        BeanA a2 = new BeanA("ceshi");
        BeanA a3 = new BeanA("just test");
        BeanB b1 = new BeanB(121);
        BeanB b2 = new BeanB(123);
        BeanB b3 = new BeanB(456);
        BeanB b4 = new BeanB(382);
        BeanB b5 = new BeanB(222);
        BaseItemA item1 = new BaseItemA(a1);
        BaseItemA item2 = new BaseItemA(a2);
        BaseItemB item3 = new BaseItemB(b1);
        BaseItemB item4 = new BaseItemB(b2);


        BaseItemA item5 = new BaseItemA(a3);
        BaseItemB item6 = new BaseItemB(b3);
        BaseItemB item7 = new BaseItemB(b4);
        BaseItemB item8 = new BaseItemB(b5);

        mData.add(item1);
        mData.add(item1);
        mData.add(item1);
        mData.add(item3);
        mData.add(item3);
        mData.add(item3);
        mData.add(item3);
        mData.add(item2);
        mData.add(item4);
        mData.add(item5);
        mData.add(item5);
        mData.add(item5);
        mData.add(item5);
        mData.add(item5);
        mData.add(item5);
        mData.add(item5);
        mData.add(item5);
        mData.add(item6);
        mData.add(item7);
        mData.add(item8);
        mData.add(item8);
        mData.add(item8);
        mData.add(item8);
        mData.add(item8);
        mData.add(item8);
        mData.add(item8);
        mData.add(item8);
        return mData;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_recycler;
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
            showToast("Age=" + bean.age);
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
            showToast("Name=" + bean.name);
        }
    }
}

package com.yushilei.commonapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.common.adapter.BaseItem;
import com.yushilei.commonapp.common.adapter.BaseRecyclerAdapter;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity {


    @Override
    protected void initView() {
        RecyclerView mRecycler = findView(R.id.recycler);
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter(this);

        BeanA a1 = new BeanA("yushilei");
        BeanA a2 = new BeanA("yushilei");
        BeanB b1 = new BeanB(121);
        BeanB b2 = new BeanB(123);
        BaseItemA item1 = new BaseItemA(a1);
        BaseItemA item2 = new BaseItemA(a2);
        BaseItemB item3 = new BaseItemB(b1);
        BaseItemB item4 = new BaseItemB(b2);

        List<BaseItem> mData = new LinkedList<>();
        mData.add(item1);
        mData.add(item2);
        mData.add(item3);
        mData.add(item4);
        adapter.addAll(mData);
        mRecycler.setAdapter(adapter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public class BaseItemB extends BaseItem<BeanB> {

        public BaseItemB(BeanB bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_b;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, BeanB beanB, int pos) {
            TextView view = (TextView) holder.findView(R.id.item_b_tv);
            ImageView img = (ImageView) holder.findView(R.id.item_b_img);

            String text = beanB.age + " 岁";
            view.setText(text);
            img.setImageResource(R.mipmap.ic_launcher);
        }
    }

    public class BaseItemA extends BaseItem<BeanA> {
        public BaseItemA(BeanA bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_a;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, BeanA beanA, int pos) {
            TextView view = (TextView) holder.findView(R.id.item_a_tv);
            ImageView img = (ImageView) holder.findView(R.id.item_a_img);

            view.setText(beanA.name);
            img.setImageResource(R.mipmap.ic_launcher_round);

        }
    }
}

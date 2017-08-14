package com.yushilei.commonapp.ui.multiholder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.ui.loadmorerecycler.LoadMoreRecyclerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiListActivity extends MvpBaseActivity<MultiListContract.Presenter> implements MultiListContract.IView {

    private MultiListAdapter mAdapter;

    @Override
    public MultiListContract.Presenter getPresenter() {
        return new MultiListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_list;
    }

    @Override
    public void initView() {
        ListView lv = findView(R.id.multi_list_lv);
        mAdapter = new MultiListAdapter(2);
        lv.setAdapter(mAdapter);
        mAdapter.setMatch(Bean.class, new BeanDelegate());
        mAdapter.setMatch(Book.class, new BookDelegate());
        mAdapter.addAll(getData());
        setOnClick(R.id.multi_add, R.id.multi_replace);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.multi_replace:
                mAdapter.replaceData(getData());
                break;
            case R.id.multi_add:
                mAdapter.addAll(getData());
                break;
        }

    }

    private List getData() {
        List data = new ArrayList();
        data.add(new Bean("测试1", 1));
        data.add(new Bean("测试2", 2));
        data.add(new Book("童话故事", R.mipmap.btn_location));
        data.add(new Book("小兵张嘎", R.mipmap.btn_location));
        data.add(new Bean("测试3", 3));
        data.add(new Book("快乐大本营", R.mipmap.btn_location));
        data.add(new Bean("测试4", 4));
        data.add(new Bean("测试5", 5));
        data.add(new Book("一千零一夜", R.mipmap.btn_location));
        data.add(new Book("小兵张嘎", R.mipmap.btn_location));
        data.add(new Book("高数", R.mipmap.btn_location));
        data.add(new Bean("测试6", 6));
        data.add(new Bean("测试7", 7));
        data.add(new Bean("测试8", 8));
        return data;
    }

    @Override
    public void onBeanNameClick(Bean bean) {
        showToast(bean.name);
        bean.name = "update+" + new Random().nextInt(10);
        mAdapter.update(bean);
    }

    @Override
    public void onBeanAgeClick(Bean bean) {
        mAdapter.remove(bean);
    }

    @Override
    public void onBeanItemLongClick(Bean bean) {
        showToast("测试2");
    }

    @Override
    public void onBookItemClick(Book book) {
        startActivity(new Intent(this, LoadMoreRecyclerActivity.class));
    }

    public class BookDelegate extends HolderDelegate<Book> {

        @Override
        public int getLayoutId() {
            return R.layout.item_holder_2;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, Book book, int pos) {
            TextView nameTv = (TextView) holder.findView(R.id.item_holder_2_tv);
            ImageView img = (ImageView) holder.findView(R.id.item_holder_2_img);
            nameTv.setText(book.name);
            img.setImageResource(book.cover);

            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<Book> holder, Book book) {
            presenter.onBookItemClick(book);
        }
    }

    public class BeanDelegate extends HolderDelegate<Bean> {
        @Override
        public int getLayoutId() {
            return R.layout.item_hodler_1;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, Bean bean, int pos) {
            TextView nameTv = (TextView) holder.findView(R.id.item_holder_1_tv);
            TextView ageTv = (TextView) holder.findView(R.id.item_holder_1_tv2);
            nameTv.setText(bean.name);

            ageTv.setText(String.valueOf(bean.age));
            nameTv.setOnClickListener(holder);
            ageTv.setOnClickListener(holder);
            holder.itemView.setOnLongClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<Bean> holder, Bean bean) {
            switch (target.getId()) {
                case R.id.item_holder_1_tv:
                    presenter.onBeanNameClick(bean);
                    break;
                case R.id.item_holder_1_tv2:
                    presenter.onBeanAgeClick(bean);
                    break;
            }
        }

        @Override
        public boolean onItemLongClick(View target, BaseRecyclerHolder<Bean> holder, Bean bean) {
            presenter.onBeanItemLongClick(bean);
            return true;
        }
    }
}

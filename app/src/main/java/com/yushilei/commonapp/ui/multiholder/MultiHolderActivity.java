package com.yushilei.commonapp.ui.multiholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.MultiHolderAdapter;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MultiHolderActivity extends MvpBaseActivity<MultiHolderContact.Presenter> implements MultiHolderContact.IView {

    private MultiHolderAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_holder;
    }

    @Override
    public void initView() {
        RecyclerView recycler = findView(R.id.activity_multi_holder_recycler);
        setOnClick(R.id.add);
        adapter = new MultiHolderAdapter();
        recycler.setAdapter(adapter);
        adapter.setMatch(Bean.class, new BeanDelegate());
        adapter.setMatch(Book.class, new BookDelegate());

        adapter.replaceData(getData());
    }

    public class AAA extends ItemWrapper<String> {
        public AAA(String bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return 0;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {

        }
    }

    @Override
    public void onClick(View v) {
        adapter.addAll(getData());
    }

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "unchecked"})
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
    public MultiHolderContact.Presenter getPresenter() {
        return new MultiHolderPresenter(this);
    }

    @Override
    public void onRemoveBean(Bean bean) {
        adapter.remove(bean);
    }

    /**
     * 适配器封装
     */
    public class BeanDelegate extends HolderDelegate<Bean> {
        @Override
        public int getLayoutId() {
            return R.layout.item_hodler_1;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, final Bean bean, int pos) {
            TextView nameTv = (TextView) holder.findView(R.id.item_holder_1_tv);
            TextView ageTv = (TextView) holder.findView(R.id.item_holder_1_tv2);
            nameTv.setText(bean.name);

            ageTv.setText(String.valueOf(bean.age));
            nameTv.setOnClickListener(holder);
            ageTv.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<Bean> holder, Bean bean) {
            switch (target.getId()) {
                case R.id.item_holder_1_tv:
                    presenter.onBeanNameClick(bean);
                    break;
                case R.id.item_holder_1_tv2:
                    presenter.onRemoveBeanClick(bean);
                    break;
            }
        }
    }

    public class BookDelegate extends HolderDelegate<Book> {

        @Override
        public int getLayoutId() {
            return R.layout.item_holder_2;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, final Book book, int pos) {
            TextView nameTv = (TextView) holder.findView(R.id.item_holder_2_tv);
            ImageView img = (ImageView) holder.findView(R.id.item_holder_2_img);
            nameTv.setText(book.name);
            img.setImageResource(book.cover);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onBookImgClick(book);
                }
            });
        }

    }

    public class Bean {
        String name;
        int age;

        public Bean(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public class Book {
        String name;
        int cover;

        public Book(String name, int cover) {
            this.name = name;
            this.cover = cover;
        }
    }
}

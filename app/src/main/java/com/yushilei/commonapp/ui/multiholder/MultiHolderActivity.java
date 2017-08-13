package com.yushilei.commonapp.ui.multiholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseHolder;
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
        adapter = new MultiHolderAdapter();
        recycler.setAdapter(adapter);
        adapter.setMatch(Bean.class, new BeanHolder());
        adapter.setMatch(Book.class, new BookHolder());
        adapter.replaceData(getData());

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
        adapter.getItemCount();
    }

    /**
     * 适配器封装
     */
    public class BeanHolder extends BaseHolder<Bean> {
        @Override
        public int getLayoutId() {
            return R.layout.item_hodler_1;
        }

        @Override
        public void onBindData(View itemView, final Bean bean, int pos) {
            TextView nameTv = (TextView) itemView.findViewById(R.id.item_holder_1_tv);
            TextView ageTv = (TextView) itemView.findViewById(R.id.item_holder_1_tv2);
            nameTv.setText(bean.name);
            ageTv.setText(String.valueOf(bean.age));

            ageTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onRemoveBeanClick(bean);
                }
            });
            nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onBeanNameClick(bean);
                }
            });
        }
    }

    public class BookHolder extends BaseHolder<Book> {

        @Override
        public int getLayoutId() {
            return R.layout.item_holder_2;
        }

        @Override
        public void onBindData(View itemView, final Book book, int pos) {
            TextView nameTv = (TextView) itemView.findViewById(R.id.item_holder_2_tv);
            ImageView img = (ImageView) itemView.findViewById(R.id.item_holder_2_img);
            nameTv.setText(book.name);
            img.setImageResource(book.cover);

            itemView.setOnClickListener(new View.OnClickListener() {
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

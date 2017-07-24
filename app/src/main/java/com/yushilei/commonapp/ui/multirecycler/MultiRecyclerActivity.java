package com.yushilei.commonapp.ui.multirecycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.widget.BezierView;
import com.yushilei.commonapp.common.widget.drop.DropCover;
import com.yushilei.commonapp.common.widget.drop.DropFake;
import com.yushilei.commonapp.common.widget.drop.DropManager;

import java.util.LinkedList;
import java.util.List;

public class MultiRecyclerActivity extends BaseActivity {

    private MultiRecyclerAdapter adapter;

    private DropCover.IDropCompletedListener dropListener = new DropCover.IDropCompletedListener() {
        @Override
        public void onCompleted(Object id, boolean explosive) {

            Log.i(getTAG(), " explosive=" + explosive);

        }
    };

    @Override
    public void initView() {
        RecyclerView mRecycler = findView(R.id.activity_multi_recycler);
        //手工画分割线
        initRecyclerDecoration(mRecycler);

        adapter = new MultiRecyclerAdapter();
        mRecycler.setAdapter(adapter);

        List<ItemWrapper> mData = getItemWrappers();

        adapter.addAll(mData);
    }


    private void initRecyclerDecoration(RecyclerView mRecycler) {
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int childCount = parent.getChildCount();
                paint.setColor(Color.parseColor("#cccccc"));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1f);
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    int bottom = child.getBottom();
                    c.drawLine(child.getLeft(), bottom, child.getRight(), bottom, paint);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 1;
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
    }

    @NonNull
    private List<ItemWrapper> getItemWrappers() {
        List<ItemWrapper> mData = new LinkedList<>();
        BeanA a1 = new BeanA("BeanA0");
        BeanA a2 = new BeanA("BeanA1");
        BeanA a3 = new BeanA("BeanA2");

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
        BaseItemC itemC = new BaseItemC(new BeanA("头部"));
        BaseItemC itemEnd = new BaseItemC(new BeanA("尾巴"));
        mData.add(itemC);
        mData.add(item1);
        mData.add(item2);
        mData.add(item3);
        mData.add(item4);

        mData.add(item5);
        mData.add(item6);
        mData.add(item7);
        mData.add(item8);
        mData.add(itemEnd);
        return mData;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_recycler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /*
        数据源封装
     */

    public class BaseItemC extends ItemWrapper<BeanA> implements View.OnClickListener {

        public BaseItemC(BeanA bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_c;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            holder.itemView.setOnClickListener(this);
            TextView tv = holder.findView(R.id.item_c_tv);
            String text = "Name=" + bean.name;
            tv.setText(text);
        }

        @Override
        public void onClick(View v) {
            showToast("Name=" + bean.name);
        }
    }

    public class BaseItemB extends ItemWrapper<BeanB> implements View.OnClickListener {

        public BaseItemB(BeanB bean) {
            super(bean);
        }

        private BaseViewHolder holder;

        @Override
        public int getLayoutRes() {
            return R.layout.item_b;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            this.holder = holder;
            TextView view = holder.findView(R.id.item_b_tv);
            ImageView img = holder.findView(R.id.item_b_img);
            BezierView fake = holder.findView(R.id.drop_fake);
            fake.setListener(new BezierView.DropCompleteListener() {
                @Override
                public void onDropComplete() {
                    BaseItemB.this.holder.findView(R.id.drop_fake).setVisibility(View.GONE);
                }
            });
            String tip = "" + bean.age;
            fake.setText(tip);


            String text = bean.age + " 岁";
            view.setText(text);
            img.setImageResource(R.mipmap.ic_launcher);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapter.remove(this);
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
            adapter.remove(this);
            showToast("Name=" + bean.name);
        }
    }
}

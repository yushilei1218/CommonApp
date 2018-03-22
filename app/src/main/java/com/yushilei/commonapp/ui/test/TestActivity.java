package com.yushilei.commonapp.ui.test;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.bean.BeanC;
import com.yushilei.commonapp.common.delegate.BeanADelegate;
import com.yushilei.commonapp.common.util.JsonUtil;
import com.yushilei.commonapp.common.widget.LoadingTextView;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import okhttp3.HttpUrl;

public class TestActivity extends BaseActivity {
    private long num = 1;
    private TextView mView;
    private BigInteger mInteger = new BigInteger("1");

    @Override
    public void initView() {
        mView = (TextView) findView(R.id.num_id_1);

        setOnClick(R.id.num_id_1);
        ListView lv = (ListView) findView(R.id.act_test_lv);
        ListView lv2 = findView(R.id.test_2_lv);

        final TextInputLayout view = (TextInputLayout) findView(R.id.test_hint_layout2);
        view.setHint(" ");
       final EditText et = (EditText) findView(R.id.test_hint_input2);
        et.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean focused = et.isFocused();
                if (focused) {
                    view.setHint("密码");
                } else {
                    view.setHint("请输入密码");
                }

            }
        },100);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view.setHint("密码");
                } else {
                    view.setHint("请输入密码");
                }
            }
        });


        MultiListAdapter adapter2 = new MultiListAdapter(1);
        adapter2.setMatch(BeanA.class, new DrawDelegate());
        lv2.setAdapter(adapter2);
        ArrayList<BeanA> root = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            root.add(new BeanA("item+ " + i));
        }
        adapter2.setRootData(root);

        MultiListAdapter adapter = new MultiListAdapter(1);
        adapter.setMatch(BeanA.class, new BeanADelegate());

        ArrayList<BeanA> newData = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            newData.add(new BeanA("item+" + i));
        }
        adapter.addAll(newData);
        lv.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num_id_1:
                mInteger = mInteger.multiply(new BigInteger("10"));
                num = num * 10;
                String s = mInteger.toString();

                mView.setText(s);
                break;
            default:
                break;
        }
    }

    private final class DrawDelegate extends HolderDelegate<BeanA> implements DrawerLayout.DrawerListener {

        @Override
        public int getLayoutId() {
            return R.layout.item_drawer;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            DrawerLayout drawerLayout = (DrawerLayout) holder.itemView;
            drawerLayout.addDrawerListener(this);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            drawerView.getParent().requestDisallowInterceptTouchEvent(true);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            drawerView.getParent().requestDisallowInterceptTouchEvent(false);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            drawerView.getParent().requestDisallowInterceptTouchEvent(false);
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }
}

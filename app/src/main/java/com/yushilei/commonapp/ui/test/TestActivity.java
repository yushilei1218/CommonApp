package com.yushilei.commonapp.ui.test;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
}

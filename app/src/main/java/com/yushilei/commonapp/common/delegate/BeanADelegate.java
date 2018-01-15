package com.yushilei.commonapp.common.delegate;

import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.bean.BeanA;

/**
 * @author shilei.yu
 * @since on 2018/1/15.
 */

public class BeanADelegate extends HolderDelegate<BeanA> {
    @Override
    public int getLayoutId() {
        return R.layout.item_a;
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
        TextView tv = (TextView) holder.findView(R.id.item_a_tv);
        tv.setText(beanA.name);
    }
}

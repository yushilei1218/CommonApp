package com.yushilei.commonapp.ui.coordinatorLayout;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.delegate.BeanADelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/1/16.
 */

public class MenuPopWindow extends PopupWindow {
    public MenuPopWindow(Context context) {
        super(context);
        View child = LayoutInflater.from(context).inflate(R.layout.layout_pop, null);
        setContentView(child);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ListView lv = (ListView) child.findViewById(R.id.pop_lv);
        MultiListAdapter adapter = new MultiListAdapter(1);
        adapter.setMatch(BeanA.class, new BeanADelegate());
        lv.setAdapter(adapter);

        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0xb2000000));
        // 这样设置才能点击屏幕外dismiss窗口

        List<BeanA> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add(new BeanA("item+" + i));
        }
        adapter.addAll(data);
    }

    @Override
    public void showAsDropDown(View v) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            v.getGlobalVisibleRect(rect);
            int h = v.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(v);
    }
}

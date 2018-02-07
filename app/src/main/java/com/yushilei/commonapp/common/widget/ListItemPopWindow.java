package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shilei.yu
 * @since on 2018/2/7.
 */

public class ListItemPopWindow<T> extends BasePopWindow {
    @BindView(R.id.pop_item_lv)
    ListView mLv;

    private final MultiListAdapter mAdapter;

    public ListItemPopWindow(Context context, Class<T> bean, HolderDelegate<? extends T> delegate) {
        super(context);
        View child = LayoutInflater.from(context).inflate(R.layout.list_item_pop, null);
        ButterKnife.bind(this, child);
        setContentView(child);
        mAdapter = new MultiListAdapter(1);
        mAdapter.setMatch(bean, delegate);
        mLv.setAdapter(mAdapter);
    }

    public void showData(List<T> data) {
        mAdapter.replaceData(data);
    }
}

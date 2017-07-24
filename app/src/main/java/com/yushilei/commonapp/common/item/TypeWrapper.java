package com.yushilei.commonapp.common.item;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
import com.yushilei.commonapp.common.bean.base.Album;
import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.bean.net.Type;

import java.util.LinkedList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 */

public class TypeWrapper extends ItemWrapper<Type> {
    public TypeWrapper(Type bean) {
        super(bean);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_album_list;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int pos) {
        TextView nameTv = holder.findView(R.id.item_album_list_name_tv);
        nameTv.setText(bean.getModuleType());

        GridView albumGrid = holder.findView(R.id.item_album_grid);
        MultiBaseAdapter adapter = new MultiBaseAdapter(1);
        albumGrid.setAdapter(adapter);
        List<Data> list = bean.getList();
        List<ItemWrapper> data = new LinkedList<>();
        for (Data item : list) {
            data.add(new AlbumWrapper(item));
        }
        adapter.addAll(data);
    }
}

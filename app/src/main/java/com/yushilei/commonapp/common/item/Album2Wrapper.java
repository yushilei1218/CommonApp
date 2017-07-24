package com.yushilei.commonapp.common.item;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.base.Album;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 */

public class Album2Wrapper extends ItemWrapper<Album> {
    public Album2Wrapper(Album bean) {
        super(bean);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_album_2;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int pos) {
        TextView tv = holder.findView(R.id.item_album_2_tv);
        SimpleDraweeView img = holder.findView(R.id.item_album_2_img);
        tv.setText(bean.getTitle());
        img.setImageURI(bean.getPic());
    }
}

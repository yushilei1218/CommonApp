package com.yushilei.commonapp.common.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.base.Album;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 */

public class AlbumWrapper extends ItemWrapper<Album> implements View.OnClickListener {
    public AlbumWrapper(Album bean) {
        super(bean);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_album;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int pos) {
        SimpleDraweeView img = (SimpleDraweeView) holder.findView(R.id.item_album_img);
        TextView tv = (TextView) holder.findView(R.id.item_album_tv);
        img.setImageURI(bean.getPic());
        tv.setText(bean.getTitle());
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), bean.getTitle(), Toast.LENGTH_SHORT).show();
    }
}

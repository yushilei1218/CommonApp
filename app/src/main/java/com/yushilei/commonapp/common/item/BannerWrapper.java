package com.yushilei.commonapp.common.item;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.base.Focus;
import com.yushilei.commonapp.common.bean.base.IFocus;
import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.bean.net.Type;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.common.widget.BannerView;

import java.util.LinkedList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/24.
 */

public class BannerWrapper extends ItemWrapper<Type> {
    public BannerWrapper(Type bean) {
        super(bean);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_focus_banner;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int pos) {
        BannerView banner = holder.findView(R.id.item_focus_banner);
        BannerView.Adapter adapter = banner.getAdapter();

        List<Data> data = bean.getList();
        if (!SetUtil.isEmpty(data)) {
            IFocus iFocus = data.get(0);
            List<Focus> focuses = iFocus.getData();
            if (!SetUtil.isEmpty(focuses)) {
                List<BannerView.BannerWrapper> list = new LinkedList<>();
                for (Focus f : focuses) {
                    list.add(new BannerWrapper.FocusWrapper(f));
                }
                adapter.addDataAndLoop(list);
            }
        }
    }

    public static final class FocusWrapper extends BannerView.BannerWrapper<Focus> implements View.OnClickListener {
        public FocusWrapper(Focus bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_banner;
        }

        @Override
        public void bindView(ViewGroup container, BannerView.BannerHolder holder, int pos) {
            SimpleDraweeView img = (SimpleDraweeView) holder.findView(R.id.item_banner_img);
            img.setImageURI(bean.getCover());
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), bean.getCover(), Toast.LENGTH_SHORT).show();
        }
    }
}

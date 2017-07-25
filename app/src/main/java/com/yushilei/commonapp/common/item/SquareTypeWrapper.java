package com.yushilei.commonapp.common.item;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.bean.base.Square;
import com.yushilei.commonapp.common.bean.net.Type;
import com.yushilei.commonapp.common.util.SetUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/25.
 */

public class SquareTypeWrapper extends ItemWrapper<Type> {
    public SquareTypeWrapper(Type bean) {
        super(bean);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_square_list;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int pos) {
        RecyclerView recycler = holder.findView(R.id.item_square_recycler);
        MultiRecyclerAdapter adapter = new MultiRecyclerAdapter();
        LinearLayoutManager layoutManager = (LinearLayoutManager) recycler.getLayoutManager();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setAdapter(adapter);
        boolean is = bean.getModuleType().equals("square");
        if (is &&! SetUtil.isEmpty(bean.getList())) {

            List<ItemWrapper> data = new LinkedList<>();
            for (int i = 0; i < bean.getList().size(); i++) {
                data.add(new SquareWrapper(bean.getList().get(i)));
            }
            adapter.addAll(data);
        }
    }

    public final class SquareWrapper extends ItemWrapper<Square> implements View.OnClickListener {
        public SquareWrapper(Square bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_square;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            SimpleDraweeView img = holder.findView(R.id.item_square_img);
            TextView tv = holder.findView(R.id.item_square_tv);
            img.setImageURI(bean.getCoverPath());
            tv.setText(bean.getTitle());
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), bean.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}

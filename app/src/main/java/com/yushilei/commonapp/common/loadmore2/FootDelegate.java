package com.yushilei.commonapp.common.loadmore2;

import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;

/**
 * @author shilei.yu
 * @since on 2017/10/14.
 */

public final class FootDelegate extends HolderDelegate<FootItem> {
    @Override
    public int getLayoutId() {
        return R.layout.footer_recycler;
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, FootItem bean, int pos) {
        View loadingLayout = holder.findView(R.id.footer_loading_layout);
        View noMoreLayout = holder.findView(R.id.footer_no_more_layout);
        switch (bean.curState) {
            case LOADING:
                loadingLayout.setVisibility(View.VISIBLE);
                noMoreLayout.setVisibility(View.GONE);
                break;
            case NO_MORE:
                loadingLayout.setVisibility(View.GONE);
                noMoreLayout.setVisibility(View.VISIBLE);
                break;
            case NORMAL:
                loadingLayout.setVisibility(View.GONE);
                noMoreLayout.setVisibility(View.GONE);
                break;
        }
    }
}

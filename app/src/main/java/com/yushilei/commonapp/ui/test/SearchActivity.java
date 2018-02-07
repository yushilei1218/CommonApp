package com.yushilei.commonapp.ui.test;

import android.view.View;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.mvp.MvpBaseActivity;
import com.yushilei.commonapp.common.widget.ListItemPopWindow;
import com.yushilei.commonapp.ui.test.bean.ResourceBean;
import com.yushilei.commonapp.ui.test.bean.StatusBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author shilei.yu
 */
public class SearchActivity extends MvpBaseActivity<SearchContract.IPresenter> implements SearchContract.IView {

    @BindView(R.id.act_search_header)
    TextView mActSearchHeader;
    @BindView(R.id.act_search_resource)
    TextView mResourceTv;
    @BindView(R.id.act_search_status)
    TextView mStatusTv;
    private ListItemPopWindow<ResourceBean> mResPop;
    private ListItemPopWindow<StatusBean> mStatusPop;

    @Override
    public SearchContract.IPresenter getPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search2;
    }

    @Override
    public void initView() {
        presenter.onStart();
    }


    @OnClick({R.id.act_search_resource, R.id.act_search_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_search_resource:
                presenter.onFilterResourceClick();
                break;
            case R.id.act_search_status:
                presenter.onFilterStatusClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void showFilterResourceView(List<ResourceBean> data) {
        if (mResPop == null) {
            mResPop = new ListItemPopWindow<>(this, ResourceBean.class, new ResourceDelegate());
        }
        mResPop.showData(data);
        mResPop.showAsDropDown(mResourceTv);
    }

    @Override
    public void showFilterStatusView(List<StatusBean> data) {
        if (mStatusPop == null) {
            mStatusPop = new ListItemPopWindow<>(this, StatusBean.class, new StatusDelegate());
        }
        mStatusPop.showData(data);
        mStatusPop.showAsDropDown(mStatusTv);
    }

    @Override
    public void showFilterResourceText(ResourceBean bean) {
        mResourceTv.setText(bean.getName());
    }

    @Override
    public void showFilterStatusText(StatusBean bean) {
        mStatusTv.setText(bean.getName());
    }

    private final class ResourceDelegate extends HolderDelegate<ResourceBean> {

        @Override
        public int getLayoutId() {
            return R.layout.item_pop_filter;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, ResourceBean bean, int pos) {
            TextView tv = (TextView) holder.findView(R.id.pop_filter_tv);
            View img = holder.findView(R.id.pop_filter_tag);
            tv.setText(bean.getName());
            int visible = bean.isSelected() ? View.VISIBLE : View.GONE;
            img.setVisibility(visible);
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<ResourceBean> holder, ResourceBean bean) {
            mResPop.dismiss();
            presenter.onFilterResourceClicked(bean);
        }
    }

    private final class StatusDelegate extends HolderDelegate<StatusBean> {

        @Override
        public int getLayoutId() {
            return R.layout.item_pop_filter;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, StatusBean bean, int pos) {
            TextView tv = (TextView) holder.findView(R.id.pop_filter_tv);
            View img = holder.findView(R.id.pop_filter_tag);

            tv.setText(bean.getName());
            int visible = bean.isSelected() ? View.VISIBLE : View.GONE;
            img.setVisibility(visible);
            holder.itemView.setOnClickListener(holder);
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<StatusBean> holder, StatusBean bean) {
            mStatusPop.dismiss();
            presenter.onFilterStatusClicked(bean);
        }
    }
}

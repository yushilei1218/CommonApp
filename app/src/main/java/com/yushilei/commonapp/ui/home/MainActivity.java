package com.yushilei.commonapp.ui.home;


import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;


import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yushilei.commonapp.R;

import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;

import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.HomeBean;
import com.yushilei.commonapp.common.constant.Constant;
import com.yushilei.commonapp.ui.adaptertest.AdapterNotifyActivity;
import com.yushilei.commonapp.ui.basedata.BaseDataActivity;
import com.yushilei.commonapp.ui.contact.ContactActivity;
import com.yushilei.commonapp.ui.filter.FilterActivity;
import com.yushilei.commonapp.ui.fragment.PagerFragmentActivity;
import com.yushilei.commonapp.ui.loadmorelistview.LoadListActivity;
import com.yushilei.commonapp.ui.loadmorerecycler.LoadMoreRecyclerActivity;
import com.yushilei.commonapp.ui.map.MapActivity;
import com.yushilei.commonapp.ui.multiholder.MultiHolderActivity;
import com.yushilei.commonapp.ui.multiholder.MultiListActivity;
import com.yushilei.commonapp.ui.multilv.MultiListViewActivity;
import com.yushilei.commonapp.ui.multirecycler.MultiRecyclerActivity;
import com.yushilei.commonapp.ui.mvp.view.HomeActivity;
import com.yushilei.commonapp.ui.myobservable.ObservableActivity;
import com.yushilei.commonapp.ui.notification.NotificationActivity;
import com.yushilei.commonapp.ui.permission.PermissionActivity;
import com.yushilei.commonapp.ui.ptr.PtrZpActivity;
import com.yushilei.commonapp.ui.rxjava.RxJavaActivity;
import com.yushilei.commonapp.ui.scroll.ScrollActivity;
import com.yushilei.commonapp.ui.swipelayout.SwipeLayoutActivity;
import com.yushilei.commonapp.ui.tab.TabsActivity;
import com.yushilei.commonapp.ui.test.TestActivity;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends BaseActivity {


    @Override
    public void initView() {
        AndPermission.with(this).permission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .requestCode(1)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        showToast("onSucceed " + requestCode + "" + grantPermissions.get(0));
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        showToast("onFailed " + requestCode + "" + deniedPermissions.get(0));
                    }
                }).start();
        GridView mGridV = findView(R.id.main_grid);
        MultiBaseAdapter adapter = new MultiBaseAdapter(1);
        mGridV.setAdapter(adapter);
        List<ItemWrapper> data = new LinkedList<>();
        HomeItem item1 = new HomeItem(new HomeBean(Constant.MULTI_RECYCLER));
        HomeItem item2 = new HomeItem(new HomeBean(Constant.MULTI_LIST_VIEW));
        HomeItem item3 = new HomeItem(new HomeBean(Constant.LOAD_MORE_RECYCLER));
        HomeItem item4 = new HomeItem(new HomeBean(Constant.ZP_PTR));
        HomeItem item5 = new HomeItem(new HomeBean(Constant.TEST));
        HomeItem item6 = new HomeItem(new HomeBean(Constant.AMAP));
        HomeItem item7 = new HomeItem(new HomeBean(Constant.XMLY));
        HomeItem item8 = new HomeItem(new HomeBean(Constant.DrawLayout));
        HomeItem item9 = new HomeItem(new HomeBean(Constant.PagerFragment));
        HomeItem item10 = new HomeItem(new HomeBean(Constant.RxJava2));
        HomeItem item11 = new HomeItem(new HomeBean(Constant.Notification));
        HomeItem item12 = new HomeItem(new HomeBean(Constant.Contact));
        HomeItem item13 = new HomeItem(new HomeBean(Constant.MultiBaseHolder));
        HomeItem item14 = new HomeItem(new HomeBean(Constant.MultiListHolder));
        HomeItem item15 = new HomeItem(new HomeBean(Constant.LOAD_LIST));
        HomeItem item16 = new HomeItem(new HomeBean(Constant.FILTER));
        HomeItem item17 = new HomeItem(new HomeBean(Constant.PERMISSION));
        HomeItem item18 = new HomeItem(new HomeBean(Constant.Observable));
        HomeItem item19 = new HomeItem(new HomeBean(Constant.Scroll));
        HomeItem item20 = new HomeItem(new HomeBean(Constant.TAB));
        HomeItem item21 = new HomeItem(new HomeBean(Constant.BASEDATA));
        HomeItem item22 = new HomeItem(new HomeBean(Constant.ADAPTER_NOTIFY));
        data.add(item1);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        data.add(item5);
        data.add(item6);
        data.add(item7);
        data.add(item8);
        data.add(item9);
        data.add(item10);
        data.add(item11);
        data.add(item12);
        data.add(item13);
        data.add(item14);
        data.add(item15);
        data.add(item16);
        data.add(item17);
        data.add(item18);
        data.add(item19);
        data.add(item20);
        data.add(item21);
        data.add(item22);
        adapter.addAll(data);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private class HomeItem extends ItemWrapper<HomeBean> implements View.OnClickListener {
        HomeItem(HomeBean bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_grid;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView tv = holder.findView(R.id.item_grid_tv);
            tv.setText(bean.name);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (bean.name) {
                case Constant.MULTI_RECYCLER:
                    intent = new Intent(MainActivity.this, MultiRecyclerActivity.class);
                    break;
                case Constant.MULTI_LIST_VIEW:
                    intent = new Intent(MainActivity.this, MultiListViewActivity.class);
                    break;
                case Constant.LOAD_MORE_RECYCLER:
                    intent = new Intent(MainActivity.this, LoadMoreRecyclerActivity.class);
                    break;
                case Constant.ZP_PTR:
                    intent = new Intent(MainActivity.this, PtrZpActivity.class);
                    break;
                case Constant.TEST:
                    intent = new Intent(MainActivity.this, TestActivity.class);
                    break;
                case Constant.AMAP:
                    intent = new Intent(MainActivity.this, MapActivity.class);
                    break;
                case Constant.XMLY:
                    intent = new Intent(MainActivity.this, HomeActivity.class);
                    break;
                case Constant.DrawLayout:
                    intent = new Intent(MainActivity.this, SwipeLayoutActivity.class);
                    break;
                case Constant.PagerFragment:
                    intent = new Intent(MainActivity.this, PagerFragmentActivity.class);
                    break;
                case Constant.RxJava2:
                    intent = new Intent(MainActivity.this, RxJavaActivity.class);
                    break;
                case Constant.Notification:
                    intent = new Intent(MainActivity.this, NotificationActivity.class);
                    break;
                case Constant.Contact:
                    intent = new Intent(MainActivity.this, ContactActivity.class);
                    break;
                case Constant.MultiBaseHolder:
                    intent = new Intent(MainActivity.this, MultiHolderActivity.class);
                    break;
                case Constant.MultiListHolder:
                    intent = new Intent(MainActivity.this, MultiListActivity.class);
                    break;
                case Constant.LOAD_LIST:
                    intent = new Intent(MainActivity.this, LoadListActivity.class);
                    break;
                case Constant.FILTER:
                    intent = new Intent(MainActivity.this, FilterActivity.class);
                    break;
                case Constant.PERMISSION:
                    intent = new Intent(MainActivity.this, PermissionActivity.class);
                    break;
                case Constant.Observable:
                    intent = new Intent(MainActivity.this, ObservableActivity.class);
                    break;
                case Constant.Scroll:
                    intent = new Intent(MainActivity.this, ScrollActivity.class);
                    break;
                case Constant.TAB:
                    intent = new Intent(MainActivity.this, TabsActivity.class);
                    break;
                case Constant.BASEDATA:
                    intent = new Intent(MainActivity.this, BaseDataActivity.class);
                    break;
                case Constant.ADAPTER_NOTIFY:
                    intent = new Intent(MainActivity.this, AdapterNotifyActivity.class);
                    break;
            }
            if (intent != null)
                MainActivity.this.startActivity(intent);
        }
    }
}

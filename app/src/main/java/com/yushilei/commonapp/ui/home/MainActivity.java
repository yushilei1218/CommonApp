package com.yushilei.commonapp.ui.home;


import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.taobao.weex.bridge.JSCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yushilei.commonapp.R;

import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;

import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.HomeBean;
import com.yushilei.commonapp.common.constant.Constant;
import com.yushilei.commonapp.common.util.JsonUtil;
import com.yushilei.commonapp.ui.adaptertest.AdapterNotifyActivity;
import com.yushilei.commonapp.ui.basedata.BaseDataActivity;
import com.yushilei.commonapp.ui.basedata.JobTypeActivity;
import com.yushilei.commonapp.ui.constraintlayout.ConstraintActivity;
import com.yushilei.commonapp.ui.contact.ContactActivity;
import com.yushilei.commonapp.ui.coordinatorLayout.CoordinatorActivity;
import com.yushilei.commonapp.ui.feizhu.FeizhuActivity;
import com.yushilei.commonapp.ui.filter.FilterActivity;
import com.yushilei.commonapp.ui.floatmenu.FloatMenuActivity;
import com.yushilei.commonapp.ui.fragment.PagerFragmentActivity;
import com.yushilei.commonapp.ui.glide.GlideActivity;
import com.yushilei.commonapp.ui.greendao.GreenDaoActivity;
import com.yushilei.commonapp.ui.loadmorelistview.LoadListActivity;
import com.yushilei.commonapp.ui.loadmorerecycler.Load2FootActivity;
import com.yushilei.commonapp.ui.loadmorerecycler.LoadMoreAdapterActivity;
import com.yushilei.commonapp.ui.loadmorerecycler.LoadMoreRecyclerActivity;
import com.yushilei.commonapp.ui.map.MapActivity;
import com.yushilei.commonapp.ui.multiholder.MultiHolderActivity;
import com.yushilei.commonapp.ui.multiholder.MultiListActivity;
import com.yushilei.commonapp.ui.multilv.MultiListViewActivity;
import com.yushilei.commonapp.ui.multirecycler.MultiRecyclerActivity;
import com.yushilei.commonapp.ui.mvp.view.HomeActivity;
import com.yushilei.commonapp.ui.mvp.view.MvpActivity;
import com.yushilei.commonapp.ui.myobservable.ObservableActivity;
import com.yushilei.commonapp.ui.notification.NotificationActivity;
import com.yushilei.commonapp.ui.permission.PermissionActivity;
import com.yushilei.commonapp.ui.ptr.PtrZpActivity;
import com.yushilei.commonapp.ui.rxjava.ProxyActivity;
import com.yushilei.commonapp.ui.rxjava.RxJavaActivity;
import com.yushilei.commonapp.ui.scroll.ScrollActivity;
import com.yushilei.commonapp.ui.search.SearchActivity;
import com.yushilei.commonapp.ui.sharedelement.SharedElementActivity;
import com.yushilei.commonapp.ui.swipelayout.SwipeLayoutActivity;
import com.yushilei.commonapp.ui.tab.TabsActivity;
import com.yushilei.commonapp.ui.test.TestActivity;
import com.yushilei.commonapp.ui.viewmodel.ViewModelActivity;
import com.yushilei.commonapp.ui.weex.WeexActivity;
import com.yushilei.commonapp.ui.weex.route.BaseRoute;
import com.yushilei.commonapp.ui.weex.route.JsBean;
import com.yushilei.commonapp.ui.weex.route.RConstant;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends BaseActivity {


    @Override
    public void initView() {
        AndPermission.with(this).permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
        HomeItem item0 = new HomeItem(new HomeBean(Constant.ZXING));
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
        HomeItem item23 = new HomeItem(new HomeBean(Constant.JOB_TYPE));
        HomeItem item24 = new HomeItem(new HomeBean(Constant.SEARCH));
        HomeItem item25 = new HomeItem(new HomeBean(Constant.Load_Test));
        HomeItem item26 = new HomeItem(new HomeBean(Constant.Load_Foot));
        HomeItem item27 = new HomeItem(new HomeBean(Constant.GREEN_DAO));
        HomeItem item28 = new HomeItem(new HomeBean(Constant.VIEW_MODEL));
        HomeItem item29 = new HomeItem(new HomeBean(Constant.CONSTRAINT));
        HomeItem item30 = new HomeItem(new HomeBean(Constant.SHARED_ELEMENT));
        HomeItem item31 = new HomeItem(new HomeBean(Constant.WEEX));
        HomeItem item32 = new HomeItem(new HomeBean(Constant.C_WEEX_TEST));
        HomeItem item33 = new HomeItem(new HomeBean(Constant.FLOAT_MENU));
        HomeItem item34 = new HomeItem(new HomeBean(Constant.PROXY));
        HomeItem item35 = new HomeItem(new HomeBean(Constant.COORDINATOR));
        HomeItem item36 = new HomeItem(new HomeBean(Constant.MVP));
        HomeItem item37 = new HomeItem(new HomeBean(Constant.GLIDE));
        HomeItem item38 = new HomeItem(new HomeBean(Constant.STATIC));
        HomeItem item39 = new HomeItem(new HomeBean(Constant.FEIZHU));
        data.add(item0);
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
        data.add(item23);
        data.add(item24);
        data.add(item25);
        data.add(item26);
        data.add(item27);
        data.add(item28);
        data.add(item29);
        data.add(item30);
        data.add(item31);
        data.add(item32);
        data.add(item33);
        data.add(item34);
        data.add(item35);
        data.add(item36);
        data.add(item37);
        data.add(item38);
        data.add(item39);
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
                case Constant.JOB_TYPE:
                    intent = new Intent(MainActivity.this, JobTypeActivity.class);
                    break;
                case Constant.SEARCH:
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    break;
                case Constant.Load_Test:
                    intent = new Intent(MainActivity.this, LoadMoreAdapterActivity.class);
                    break;
                case Constant.Load_Foot:
                    intent = new Intent(MainActivity.this, Load2FootActivity.class);
                    break;
                case Constant.GREEN_DAO:
                    intent = new Intent(MainActivity.this, GreenDaoActivity.class);
                    break;
                case Constant.VIEW_MODEL:
                    intent = new Intent(MainActivity.this, ViewModelActivity.class);
                    break;
                case Constant.CONSTRAINT:
                    intent = new Intent(MainActivity.this, ConstraintActivity.class);
                    break;
                case Constant.SHARED_ELEMENT:
                    intent = new Intent(MainActivity.this, SharedElementActivity.class);
                    break;
                case Constant.WEEX:
                    JsBean<BeanA> test = new JsBean<>(RConstant.ROUTE_KEY1, "http://doc.zwwill.com/yanxuan/jsbundles/index.js", new BeanA("Bean 111"));
                    useRoute(test, new JSCallback() {
                        @Override
                        public void invoke(Object data) {
                            showToast("JSCallback invoke " + data.toString());
                        }

                        @Override
                        public void invokeAndKeepAlive(Object data) {
                            showToast("JSCallback invokeAndKeepAlive " + data.toString());
                        }
                    });
                    return;
                case Constant.C_WEEX_TEST:
                    JsBean<BeanA> test2 = new JsBean<>(RConstant.ROUTE_KEY1, "https://c-m-bucket.zhaopin.cn/next/zpd/zpdDiscoverHome.weex.8441ab.js", new BeanA("Bean 111"));
                    useRoute(test2, null);
                    return;
                case Constant.ZXING:
                    openZxing();
                    break;
                case Constant.FLOAT_MENU:
                    intent = new Intent(MainActivity.this, FloatMenuActivity.class);
                    break;
                case Constant.PROXY:
                    intent = new Intent(MainActivity.this, ProxyActivity.class);
                    break;
                case Constant.COORDINATOR:
                    intent = new Intent(MainActivity.this, CoordinatorActivity.class);
                    break;
                case Constant.MVP:
                    intent = new Intent(MainActivity.this, MvpActivity.class);
                    break;
                case Constant.GLIDE:
                    intent = new Intent(MainActivity.this, GlideActivity.class);
                    break;
                case Constant.STATIC:
                    intent = new Intent(MainActivity.this, com.yushilei.commonapp.ui.test.SearchActivity.class);
                    break;
                case Constant.FEIZHU:
                    intent = new Intent(MainActivity.this, FeizhuActivity.class);
                    break;
                default:
                    break;

            }
            if (intent != null) {
                MainActivity.this.startActivity(intent);
            }
        }
    }

    private void useRoute(JsBean test, JSCallback callback) {


        BaseRoute route = BaseRoute.newInstance(JsonUtil.toJson(test), callback);
        if (route != null) {
            route.invoke();
        } else {
            showToast("BaseRoute 为空");
        }
    }

    private void openZxing() {
        AndPermission.with(this).permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .requestCode(1)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        new IntentIntegrator(MainActivity.this).initiateScan();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        showToast("onFailed " + requestCode + "" + deniedPermissions.get(0));
                    }
                }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String weexUrl = result.getContents();
            if (weexUrl == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + weexUrl, Toast.LENGTH_LONG).show();
                Log.d(getTAG(), weexUrl);
                WeexActivity.invoke(this, weexUrl);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

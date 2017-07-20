package com.yushilei.commonapp.ui.map;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.util.SetUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MapActivity extends BaseActivity implements Inputtips.InputtipsListener, AMapLocationListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener {

    private MapView mMapView;
    private MultiRecyclerAdapter mPoiAdapter;
    private EditText mKeyWordInput;
    private AMap aMap;
    private TextView logTV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取地图控件引用
        mMapView = findView(R.id.map_v);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();

        aMap.setOnCameraChangeListener(this);


        RecyclerView recycler = findView(R.id.map_recycler);

        mKeyWordInput = findView(R.id.map_key_word_input);
        logTV = findView(R.id.log_tv);

        mPoiAdapter = new MultiRecyclerAdapter();
        recycler.setAdapter(mPoiAdapter);


        mKeyWordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                startTipSearch(s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setOnClick(R.id.btn_location);
    }

    private void startTipSearch(CharSequence s) {
        InputtipsQuery inputquery = new InputtipsQuery(s.toString(), "北京");
        inputquery.setCityLimit(true);//限制在当前城市
        Inputtips inputTips = new Inputtips(this, inputquery);

        inputTips.setInputtipsListener(MapActivity.this);
        inputTips.requestInputtipsAsyn();
    }


    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_location:
                startLocation();
                break;
        }
    }


    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void startLocation() {
        initLocationComponent();
        showToast("定位开始");
        //启动定位
        mLocationClient.startLocation();
    }

    private void initLocationComponent() {
        //声明mLocationOption对象
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位监听
        mLocationClient.setLocationListener(this);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);


        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /*高德自动提示*/
    @Override
    public void onGetInputtips(List<Tip> list, int i) {


    }

    /*高德定位*/
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        showToast("定位结束");

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //绘制marker
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                Marker marker = aMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(getResources(), R.mipmap.marker)))
                        .draggable(true));
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        latLng, 18, 30, 30)));

            }
            logTV.setText(aMapLocation.toString());
        }

    }

    /*高德地图拖动*/
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLng target = cameraPosition.target;

        PoiSearch.Query query = new PoiSearch.Query("", "190400", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(getApplicationContext(), query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(target.latitude,
                target.longitude), 1000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /*POI 搜索回调*/
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        ArrayList<PoiItem> pois = poiResult.getPois();
        mPoiAdapter.addAll(getItemWrappersByPoiItems(pois));
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private List<ItemWrapper> getItemWrappersByTips(List<Tip> list) {
        if (SetUtil.isEmpty(list))
            return null;
        List<ItemWrapper> data = new LinkedList<>();
        for (Tip tip : list) {
            data.add(new TipSelectWrapper(tip));
        }
        return data;
    }

    private List<ItemWrapper> getItemWrappersByPoiItems(List<PoiItem> list) {
        if (SetUtil.isEmpty(list))
            return null;
        List<ItemWrapper> data = new LinkedList<>();
        for (PoiItem tip : list) {
            data.add(new PoiItemWrapper(tip));
        }
        return data;
    }


    private class PoiItemWrapper extends ItemWrapper<PoiItem> {
        PoiItemWrapper(PoiItem bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_tip_select;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView nameTv = holder.findView(R.id.item_tip_select_name);
            TextView addressTv = holder.findView(R.id.item_tip_select_address);
            nameTv.setText(bean.getTitle());


            addressTv.setText(bean.getAdName());
        }
    }

    private class TipSelectWrapper extends ItemWrapper<Tip> {
        TipSelectWrapper(Tip bean) {
            super(bean);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_tip_select;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int pos) {
            TextView nameTv = holder.findView(R.id.item_tip_select_name);
            TextView addressTv = holder.findView(R.id.item_tip_select_address);
            nameTv.setText(bean.getName());
            addressTv.setText(bean.getAddress());
        }
    }
}

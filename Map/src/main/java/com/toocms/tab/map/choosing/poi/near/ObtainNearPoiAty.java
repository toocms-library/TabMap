package com.toocms.tab.map.choosing.poi.near;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.toocms.tab.map.R;
import com.toocms.tab.map.TabMapApi;
import com.toocms.tab.map.choosing.base.ChoosingBaseAty;
import com.toocms.tab.map.choosing.poi.PoiListAdapter;
import com.toocms.tab.map.choosing.poi.PoiItemDecoration;
import com.toocms.tab.map.choosing.poi.search.ObtainSearchPoiAty;
import com.toocms.tab.map.choosing.config.ChoosingConfig;
import com.toocms.tab.map.poi.TooCMSPoiApi;
import com.toocms.tab.toolkit.permission.PermissionFail;
import com.toocms.tab.toolkit.permission.PermissionSuccess;

/**
 * 获取附近POI列表页
 * <p>
 * 一般用于用户端供用户选择收获地址
 * <p>
 * Author：Zero
 * Date：2018/6/29 17:07
 *
 * @version v1.0
 */
public class ObtainNearPoiAty extends ChoosingBaseAty implements View.OnClickListener {

    private static final int REQUEST_CODE = 0x111;

    private TextView tvSearch;
    private RecyclerView recyclerView;
    private PoiListAdapter adapter;

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitle("请选择收货地址");
        initControls();
        super.onCreateActivity(savedInstanceState);
        // 初始化列表
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        PoiItemDecoration decoration = new PoiItemDecoration(this);
        recyclerView.addItemDecoration(decoration);
        adapter = new PoiListAdapter(this, recyclerView);
        recyclerView.setAdapter(adapter);
        // 地图可视区域改变监听
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                doSearch(cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_obtain_near_poi;
    }

    @Override
    protected void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (ChoosingConfig.poiResultListener != null) {
            ChoosingConfig.poiResultListener.onPoiCancel();
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CODE:
                finish();
                break;
        }
    }

    private void initControls() {
        mapView = findViewById(R.id.obtain_near_poi_map);
        recyclerView = findViewById(R.id.obtain_near_poi_items);
        tvSearch = findViewById(R.id.obtain_near_poi_search);
        tvSearch.setCompoundDrawablesWithIntrinsicBounds(options.getLocationResId(), 0, 0, 0);
        tvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Bundle bundle = new Bundle();
        bundle.putString("cityCode", cityCode);
        bundle.putInt("BackResId", options.getBackResId());
        bundle.putInt("SearchResId", options.getSearchResId());
        bundle.putInt("TitlebarBgColor", options.getTitlebarBgColor());
        startActivityForResult(ObtainSearchPoiAty.class, bundle, REQUEST_CODE);
    }

    private void doSearch(double latitude, double longitude) {
        TooCMSPoiApi.getInstance(this).doSearchPoi(
                latitude,
                longitude,
                1000,
                null,
                TabMapApi.DEFAULT_POI_TYPE,
                1,
                40,
                new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        adapter.setData(poiResult.getPois());
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
    }

    @PermissionSuccess(requestCode = PERMISSON_REQUESTCODE)
    public void requestSuccess() {
        startLocation();
    }

    @PermissionFail(requestCode = PERMISSON_REQUESTCODE)
    public void requestFail() {
        doSearch(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    }
}

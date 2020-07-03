package com.toocms.tab.map.choosing.base;

import android.Manifest;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.toocms.tab.map.R;
import com.toocms.tab.map.choosing.config.ChoosingConfig;
import com.toocms.tab.map.choosing.config.PageOptions;
import com.toocms.tab.map.location.TooCMSLocationApi;
import com.toocms.tab.map.location.listener.LocationListener;
import com.toocms.tab.map.utils.LocationUtils;
import com.toocms.tab.ui.BaseActivity;
import com.toocms.tab.ui.BasePresenter;

/**
 * Author：Zero
 * Date：2020/6/19 11:14
 *
 * @version v1.0
 */

public abstract class ChoosingBaseAty extends BaseActivity {

    protected static final int PERMISSON_REQUESTCODE = 0;   // 请求权限标识
    protected static final int DEFAULT_RANGE = 1000;    // 默认搜索范围，单位：米
    protected static final double DEFAULT_LONGITUDE = 39.924206;    // 地图默认经度
    protected static final double DEFAULT_LATITUDE = 116.397865;    // 地图默认纬度

    protected MapView mapView;
    protected TextView title;

    protected Marker marker; // 大头针
    protected AMap aMap;  // 地图
    protected LatLonPoint latLonPoint;   // 经纬度点

    protected PageOptions options;    // 页面配置选项
    protected String cityCode;    // 城市编码
    protected boolean isFirstLocation = true; // 是否为第一次定位，用于区分是否显示地图移动动画

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        // 标题栏
        mActionBar.setNavigationIcon(options.getBackResId());
        mActionBar.setBackgroundResource(options.getTitlebarBgColor());
        title = mActionBar.findViewById(R.id.title);
        title.setTextColor(getResources().getColor(options.getTitleColor()));
        // 初始化地图
        mapView.onCreate(savedInstanceState);
        // 初始化地图控制器
        aMap = mapView.getMap();
        aMap.getUiSettings().setCompassEnabled(options.isShowCompass());   // 设置指南针显示
        aMap.getUiSettings().setZoomControlsEnabled(options.isShowZoomControls()); // 设置缩放控制显示
        aMap.getUiSettings().setMyLocationButtonEnabled(options.isShowLocation());  // 设置定位按钮显示
        // 定位按钮点击
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                requestPermissions(PERMISSON_REQUESTCODE, Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            @Override
            public void deactivate() {
                TooCMSLocationApi.getInstance(getApplicationContext()).release();
            }
        });
        // 地图加载完毕监听
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInMapCenter();
                // 检查位置服务是否打开
                inspectLocationService();
            }
        });
    }

    @Override
    protected void initialized() {
        Bundle bundle = getIntent().getExtras();
        options = (PageOptions) bundle.getSerializable("options");
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void requestData() {
    }

    protected abstract void onClickBack();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        TooCMSLocationApi.getInstance(this).release();
        ChoosingConfig.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                onClickBack();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 添加大头针到地图中心位置
     */
    private void addMarkerInMapCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        MarkerOptions options = new MarkerOptions();
        // 设置marker锚点在最底部
        options.anchor(0.5f, 1f);
        // 设置marker图标
        if (this.options.getMarkerResId() != 0)
            options.icon(BitmapDescriptorFactory.fromResource(this.options.getMarkerResId()));
        // 添加marker到地图
        marker = aMap.addMarker(options);
        // 设置marker在屏幕上，不跟随地图移动
        marker.setPositionByPixels(screenPosition.x, screenPosition.y);
        marker.setZIndex(0);
    }

    /**
     * 开始定位
     */
    protected void startLocation() {
        TooCMSLocationApi.getInstance(this).start(new LocationListener() {
            @Override
            public void onLocationSuccess(AMapLocation aMapLocation) {
                cityCode = aMapLocation.getCityCode();
                LatLng curLatlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                latLonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);
                if (isFirstLocation) {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, options.getMapZoomLevel()));
                    isFirstLocation = false;
                } else {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, options.getMapZoomLevel()), 500, null);
                }
            }

            @Override
            public void onLocationFail() {
            }
        });
    }

    /**
     * 检查是否开启位置服务
     * 打开 → 定位
     * 未打开 → 提示去打开
     */
    public void inspectLocationService() {
        if (!LocationUtils.isLocationEnabled(this))
            LocationUtils.toOpenGPS(this);
        else {
            requestPermissions(PERMISSON_REQUESTCODE, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }
}

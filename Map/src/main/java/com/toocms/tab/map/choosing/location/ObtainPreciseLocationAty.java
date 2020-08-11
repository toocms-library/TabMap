package com.toocms.tab.map.choosing.location;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.toocms.tab.map.R;
import com.toocms.tab.map.choosing.base.ChoosingBaseAty;
import com.toocms.tab.map.choosing.config.ChoosingConfig;
import com.toocms.tab.toolkit.ListUtils;
import com.toocms.tab.toolkit.LogUtil;
import com.toocms.tab.toolkit.permission.PermissionFail;
import com.toocms.tab.toolkit.permission.PermissionSuccess;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 获取精确位置页
 * <p>
 * 一般用于商家端供商家选择精确的店铺位置以保证导航的准确性
 * <p>
 * Author：Zero
 * Date：2018/7/21 16:59
 *
 * @version v1.0
 */
public class ObtainPreciseLocationAty extends ChoosingBaseAty implements View.OnClickListener {

    TextView tvName;
    TextView tvAddress;
    TextView tvSure;

    GeocodeSearch geocodeSearch;  // 逆地理编码查询

    private DecimalFormat decimalFormat = new DecimalFormat("#.######");
    private LocationResult result = new LocationResult();  // 定位结果

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        // 标题栏
        mActionBar.setNavigationIcon(options.getBackResId());
        setTitle("选择地图坐标");
        // 初始化控件
        initControls();
        super.onCreateActivity(savedInstanceState);
        // 地图可视区域改变监听
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                result.setLatitude(decimalFormat.format(cameraPosition.target.latitude));
                result.setLongitude(decimalFormat.format(cameraPosition.target.longitude));
                doSearch(cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });

        // 初始化地理编码类
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int resultID) {
                if (resultID == AMapException.CODE_AMAP_SUCCESS) {
                    if (regeocodeResult == null) return;
                    RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                    if (regeocodeAddress == null) return;

                    String name = null;
                    String address = null;

                    // 若有poi返回则显示poi名称和地址，否则返回乡镇名称和地址
                    List<PoiItem> poiItems = regeocodeAddress.getPois();    // poi
                    String township = regeocodeAddress.getTownship();   // 乡镇名称
                    String province = regeocodeAddress.getProvince();
                    String city = regeocodeAddress.getCity();
                    String district = regeocodeAddress.getDistrict();
                    String provinceCode = "";
                    String cityCode = regeocodeAddress.getCityCode();
                    String districtCode = regeocodeAddress.getAdCode();

                    if (!ListUtils.isEmpty(poiItems)) {
                        PoiItem poiItem = poiItems.get(0);
                        name = poiItem.getTitle();
                        address = poiItem.getSnippet();
                    } else if (!TextUtils.isEmpty(township)) {
                        name = township;
                        if (TextUtils.equals(province, city)) {
                            address = city + district + township;
                        } else {
                            address = province + city + district + township;
                        }
                    } else {
                        name = regeocodeAddress.getFormatAddress();
                    }

                    // 什么都没有直接返回
                    if (TextUtils.isEmpty(name) && TextUtils.isEmpty(address)) return;
                    // 名字/地址有一个为空则隐藏地址只显示名称
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address)) {
                        tvAddress.setVisibility(View.GONE);
                    } else {
                        tvAddress.setVisibility(View.VISIBLE);
                    }
                    // 回调赋值
                    result.setName(name);
                    result.setAddress(address);
                    result.setProvince(province);
                    result.setProvinceCode(provinceCode);
                    result.setCity(city);
                    result.setCityCode(cityCode);
                    result.setDistrict(district);
                    result.setDistrictCode(districtCode);
                    Log.e("result", result.toString());
                    // 控件赋值
                    tvName.setText(name);
                    tvAddress.setText(address);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int resultID) {

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_obtain_precise_location;
    }

    @Override
    protected void initialized() {
        super.initialized();
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);    // 经纬度四舍五入
    }

    @Override
    protected void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (ChoosingConfig.locationResultListener != null) {
            ChoosingConfig.locationResultListener.onLocationCancel();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (ChoosingConfig.locationResultListener != null) {
            ChoosingConfig.locationResultListener.onLocationResult(result);
        }
        finish();
    }

    private void initControls() {
        mapView = findViewById(R.id.obtain_precise_location_map);
        tvName = findViewById(R.id.obtain_precise_location_name);
        tvAddress = findViewById(R.id.obtain_precise_location_address);
        tvSure = findViewById(R.id.obtain_precise_location_sure);
        tvSure.setOnClickListener(this);
    }

    private void doSearch(double longitude, double latitude) {
        latLonPoint = new LatLonPoint(longitude, latitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, DEFAULT_RANGE, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

    @PermissionSuccess(requestCode = PERMISSON_REQUESTCODE)
    public void requestSuccess() {
        startLocation();
    }

    @PermissionFail(requestCode = PERMISSON_REQUESTCODE)
    public void requestFail() {
        doSearch(DEFAULT_LONGITUDE, DEFAULT_LATITUDE);
    }
}

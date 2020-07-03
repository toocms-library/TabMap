<p align="center">
  <img src="https://avatars3.githubusercontent.com/u/38806334?s=400&u=b20d7b719e126e45e3d45c0ff04d0597ae3ed703&v=4" width="220" height="220" alt="Banner" />
</p>

# TabMap框架集成文档

[![](https://jitpack.io/v/toocms-library/TabMap.svg)](https://jitpack.io/#toocms-library/Tab)&#160;&#160;&#160;&#160;&#160;![Support](https://img.shields.io/badge/API-19+-4BC51D.svg)&#160;&#160;&#160;&#160;&#160;[![Tab Update](https://img.shields.io/badge/更新-记录-4BC51D.svg)](https://github.com/toocms-library/TabMap/releases)&#160;&#160;&#160;&#160;&#160;![Author](https://img.shields.io/badge/Author-Zero-4BC51D.svg)

## 添加Gradle依赖
- 在模块目录下的build.gradle文件的dependencies添加

```
dependencies {
    implementation 'com.github.toocms-library:TabShare:1.0.0.200703-alpha'
    implementation 'com.amap.api:3dmap:7.5.0'       // 地图，可选，如依赖必须同时依赖搜索和定位
    implementation 'com.amap.api:search:7.3.0'      // 搜索，可选，须与地图并存
    implementation 'com.amap.api:location:5.0.0'    // 定位，可单选，无地图需求时可单独依赖定位包
}
```

## 定位

- 基础定位

```
TabMapApi.getLocationApi(this)
    .setLocationOption(new AMapLocationClientOption())  // 可选项，自定义定位参数
    .start(new LocationListener() {
        @Override
        public void onLocationSuccess(AMapLocation aMapLocation) {
            showDialog("定位结果", aMapLocation.toString());
        }

        @Override
        public void onLocationFail() {
        }
    });
```

- 获取最近的AOI/POI

```
TabMapApi.getLocationApi(this)
    .setLocationOption(new AMapLocationClientOption())  // 可选项，自定义定位参数
    .start(new NearestBuildingListener() {
        @Override
        public void onSearchNearestBuilding(Building building) {
            showDialog("当前位置", "地址：" + building.getName() + "\n" + "经度：" + building.getLongitude() + "\n" + "纬度：" + building.getLatitude());
        }
    });
```

- 停止定位

```
TabMapApi.getLocationApi(this).stop();
```

- 释放内存

```
TabMapApi.getLocationApi(this).release();
```

## POI

- 查询附近POI

```
TabMapApi.getPoiApi(this)
    .doSearchPoi(
        latitude,    // 纬度
        longitude,  // 经度
        1000,        // 查询半径
        null,         // 关键字
        TabMapApi.DEFAULT_POI_TYPE,     // POI类型，示例字段为全类型
        1,           // 页码
        10,         // 每页返回条数
        new PoiSearch.OnPoiSearchListener() {
          @Override
          public void onPoiSearched(PoiResult poiResult, int i) {
             showItemsDialog("周边POI", pois, null);
          }

          @Override
          public void onPoiItemSearched(PoiItem poiItem, int i) {
          }
        });
```

- 根据关键字查询POI

```
TabMapApi.getPoiApi(this)
    .doSearchPoi(
        "天安门",      // 关键字
        TabMapApi.DEFAULT_POI_TYPE,     // POI类型，示例字段为全类型
        "北京",         // 城市
        1,               // 页码
        10,             // 每页返回条数
        new PoiSearch.OnPoiSearchListener() {
          @Override
          public void onPoiSearched(PoiResult poiResult, int i) {
               showItemsDialog("关键字POI", pois, null);
          }

          @Override
          public void onPoiItemSearched(PoiItem poiItem, int i) {
          }
        });
```

## 启动地址选择页面

- 精准位置选择页面（一般用于商家端选择位置）

```
TabMapApi.getChoosingApi(this)
    .options(new PageOptions()                                   // 配置可选项
          .setTitlebarBgColor(R.color.black))                    // 设置标题栏背景颜色，默认白色
          .setBackResId(R.drawable.ic_back)                      // 返回按钮
          .setTitleColor(R.color.white)                          // 设置标题文字颜色，默认黑色
          .setLocationResId(R.drawable.flag_location)            // 附近POI页搜索框的位置图标
          .setSearchResId(R.drawable.ic_search)                  // 关键字搜索POI页的放大镜图标
          .setMarkerResId(R.drawable.ic_input_arrow)             // 设置地图中心点指针图标，默认为高德自带指针
          .setCompassEnable(false)                               // 地图指南针图标是否显示，默认显示
          .setLocationEnable(false)                              // 地图定位按钮是否显示，默认显示
          .setZoomControlsEnable(true)                           // 设置缩放按钮是否显示，默认不显示
          .setMapZoomLevel(18)                                   // 设置定位完毕的地图缩放级别，默认18
    .startPreciseLocationAty(new LocationResultListener() {
       @Override
       public void onLocationResult(LocationResult result) {
          showDialog("定位结果", "名称：" + result.getName() + "\n" + "地址：" + result.getAddress() + "\n" + "经度：" + result.getLongitude() + "\n" + "纬度：" + result.getLatitude());
       }

       @Override
       public void onLocationCancel() {
          showToast("取消定位");
       }
    });
```

- 附近POI/搜索POI（一般用于用户端选择配送地址）

```
TabMapApi.getChoosingApi(this)
    .options(new PageOptions()                                   // 配置可选项
          .setTitlebarBgColor(R.color.black))                    // 设置标题栏背景颜色，默认白色
          .setBackResId(R.drawable.ic_back)                      // 返回按钮
          .setTitleColor(R.color.white)                          // 设置标题文字颜色，默认黑色
          .setLocationResId(R.drawable.flag_location)            // 附近POI页搜索框的位置图标
          .setSearchResId(R.drawable.ic_search)                  // 关键字搜索POI页的放大镜图标
          .setMarkerResId(R.drawable.ic_input_arrow)             // 设置地图中心点指针图标，默认为高德自带指针
          .setCompassEnable(false)                               // 地图指南针图标是否显示，默认显示
          .setLocationEnable(false)                              // 地图定位按钮是否显示，默认显示
          .setZoomControlsEnable(true)                           // 设置缩放按钮是否显示，默认不显示
          .setMapZoomLevel(18)                                   // 设置定位完毕的地图缩放级别，默认18
    .startNearPoiAty(new PoiResultListener() {
       @Override
       public void onPoiResult(PoiResult result) {
          showDialog("定位结果", "名称：" + result.getName() + "\n" + "地址：" + result.getAddress() + "\n" + "经度：" + result.getLongitude() + "\n" + "纬度：" + result.getLatitude());
       }

       @Override
       public void onPoiCancel() {
          showToast("取消选择");
       }
    });
```
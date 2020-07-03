package com.toocms.tab.map.choosing.config;

import com.toocms.tab.map.R;

import java.io.Serializable;

/**
 * 页面配置
 * <p>
 * Author：Zero
 * Date：2020/7/1 15:41
 *
 * @version v1.0
 */

public class PageOptions implements Serializable {

    /**
     * 返回按钮资源ID
     */
    private int backResId;

    /**
     * 标题栏背景色
     */
    private int titlebarBgColor;

    /**
     * 标题文字颜色
     */
    private int titleColor;

    /**
     * 地图缩放级别
     */
    private int mapZoomLevel;

    /**
     * 地图中心气泡图标
     */
    private int markerResId;

    /**
     * 附近POI页搜索框的位置图标（startNearPoiAty方法有效）
     */
    private int locationResId;

    /**
     * 放大镜图标（startNearPoiAty方法有效）
     */
    private int searchResId;

    /**
     * 是否显示指南针
     */
    private boolean showCompass;

    /**
     * 是否显示缩放控制
     */
    private boolean showZoomControls;

    /**
     * 是否显示定位按钮
     */
    private boolean showLocation;

    public PageOptions() {
        backResId = R.drawable.ic_menu_back_black;
        titlebarBgColor = R.color.white;
        titleColor = R.color.black;
        mapZoomLevel = 18;
        markerResId = 0;
        locationResId = R.drawable.ic_location_on_black;
        searchResId = R.drawable.ic_tabmap_flag_search;
        showCompass = true;
        showZoomControls = false;
        showLocation = true;
    }

    /**
     * 获取返回按钮
     *
     * @return
     */
    public int getBackResId() {
        return backResId;
    }

    /**
     * 设置返回按钮
     *
     * @param backResId
     */
    public PageOptions setBackResId(int backResId) {
        this.backResId = backResId;
        return this;
    }

    /**
     * 获取标题栏背景色
     *
     * @return
     */
    public int getTitlebarBgColor() {
        return titlebarBgColor;
    }

    /**
     * 设置标题栏背景色，默认白色
     *
     * @param titlebarBgColor
     */
    public PageOptions setTitlebarBgColor(int titlebarBgColor) {
        this.titlebarBgColor = titlebarBgColor;
        return this;
    }

    /**
     * 获取标题文字颜色
     *
     * @return
     */
    public int getTitleColor() {
        return titleColor;
    }

    /**
     * 设置标题文字颜色，默认黑色
     *
     * @param titleColor
     * @return
     */
    public PageOptions setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    /**
     * 获取地图缩放级别
     *
     * @return
     */
    public int getMapZoomLevel() {
        return mapZoomLevel;
    }

    /**
     * 设置地图缩放级别，默认18
     *
     * @param mapZoomLevel
     */
    public PageOptions setMapZoomLevel(int mapZoomLevel) {
        this.mapZoomLevel = mapZoomLevel;
        return this;
    }

    /**
     * 获取地图中心气泡图标
     *
     * @return
     */
    public int getMarkerResId() {
        return markerResId;
    }

    /**
     * 设置地图中心气泡图标，默认为高德默认气泡
     *
     * @param markerResId
     */
    public PageOptions setMarkerResId(int markerResId) {
        this.markerResId = markerResId;
        return this;
    }

    /**
     * 获取附近POI页搜索框的位置图标
     *
     * @return
     */
    public int getLocationResId() {
        return locationResId;
    }

    /**
     * 设置附近POI页搜索框的位置图标（startNearPoiAty方法有效）
     *
     * @param locationResId
     * @return
     */
    public PageOptions setLocationResId(int locationResId) {
        this.locationResId = locationResId;
        return this;
    }

    /**
     * 获取放大镜图标
     *
     * @return
     */
    public int getSearchResId() {
        return searchResId;
    }

    /**
     * 设置放大镜图标（startNearPoiAty方法有效）
     *
     * @param searchResId
     */
    public PageOptions setSearchResId(int searchResId) {
        this.searchResId = searchResId;
        return this;
    }

    /**
     * 是否显示指南针
     *
     * @return
     */
    public boolean isShowCompass() {
        return showCompass;
    }

    /**
     * 设置是否显示指南针，默认显示
     *
     * @param showCompass
     */
    public PageOptions setCompassEnable(boolean showCompass) {
        this.showCompass = showCompass;
        return this;
    }

    /**
     * 是否显示缩放控制
     *
     * @return
     */
    public boolean isShowZoomControls() {
        return showZoomControls;
    }

    /**
     * 设置是否显示缩放控制，默认不显示
     *
     * @param showZoomControls
     */
    public PageOptions setZoomControlsEnable(boolean showZoomControls) {
        this.showZoomControls = showZoomControls;
        return this;
    }

    /**
     * 是否显示定位按钮
     *
     * @return
     */
    public boolean isShowLocation() {
        return showLocation;
    }

    /**
     * 设置是否显示定位按钮，默认显示
     *
     * @param showLocation
     */
    public PageOptions setLocationEnable(boolean showLocation) {
        this.showLocation = showLocation;
        return this;
    }
}

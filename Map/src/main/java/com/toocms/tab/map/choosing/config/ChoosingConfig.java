package com.toocms.tab.map.choosing.config;

import com.toocms.tab.map.choosing.listener.LocationResultListener;
import com.toocms.tab.map.choosing.listener.PoiResultListener;

public class ChoosingConfig {

    public static LocationResultListener locationResultListener;
    public static PoiResultListener poiResultListener;

    /**
     * 释放监听器
     */
    public static void release() {
        locationResultListener = null;
        poiResultListener = null;
    }
}

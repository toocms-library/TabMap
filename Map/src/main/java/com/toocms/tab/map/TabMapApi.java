package com.toocms.tab.map;

import android.content.Context;

import com.toocms.tab.map.choosing.TooCMSChoosingApi;
import com.toocms.tab.map.location.TooCMSLocationApi;
import com.toocms.tab.map.poi.TooCMSPoiApi;

/**
 * 地图总集合类
 * <p>
 * Author：Zero
 * Date：2020/6/30 17:32
 *
 * @version v1.0
 */

public final class TabMapApi {

    /**
     * 所有POI类型
     */
    public static final String DEFAULT_POI_TYPE = "010000|020000|030000|040000|050000|060000|070000|080000|090000|100000|110000|120000|130000|140000|150000|160000|170000|180000|190000|200000|220000|970000|990000";

    /**
     * 获取定位 API
     *
     * @param context
     * @return
     */
    public static TooCMSLocationApi getLocationApi(Context context) {
        return TooCMSLocationApi.getInstance(context);
    }

    /**
     * 获取POI API
     *
     * @param context
     * @return
     */
    public static TooCMSPoiApi getPoiApi(Context context) {
        return TooCMSPoiApi.getInstance(context);
    }

    /**
     * 获取选择具体坐标点 API
     *
     * @param context
     * @return
     */
    public static TooCMSChoosingApi getChoosingApi(Context context) {
        return TooCMSChoosingApi.getInstance(context);
    }

    /**
     * 释放
     *
     * @param context
     */
    public static void release(Context context) {
        getLocationApi(context).release();
        getPoiApi(context).release();
        getChoosingApi(context).release();
    }
}

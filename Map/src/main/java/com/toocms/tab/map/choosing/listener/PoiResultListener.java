package com.toocms.tab.map.choosing.listener;

import com.amap.api.services.core.PoiItem;

/**
 * poi选择结果监听
 * <p>
 * Author：Zero
 * Date：2020/6/17 14:26
 *
 * @version v1.0
 */

public interface PoiResultListener {

    /**
     * 当有返回结果
     *
     * @param poiItem
     */
    void onPoiResult(PoiItem poiItem);

    /**
     * 当取消选点
     */
    void onPoiCancel();
}

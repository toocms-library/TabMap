package com.toocms.tab.map.choosing.listener;

import com.toocms.tab.map.choosing.location.LocationResult;

/**
 * 选择位置回调监听
 * <p>
 * Author：Zero
 * Date：2020/6/16 18:19
 *
 * @version v1.0
 */

public interface LocationResultListener {

    /**
     * 当有返回结果
     *
     * @param result
     */
    void onLocationResult(LocationResult result);

    /**
     * 当取消选点
     */
    void onLocationCancel();
}

package com.toocms.tab.map.choosing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.toocms.tab.map.choosing.config.PageOptions;
import com.toocms.tab.map.choosing.listener.LocationResultListener;
import com.toocms.tab.map.choosing.listener.PoiResultListener;
import com.toocms.tab.map.choosing.location.ObtainPreciseLocationAty;
import com.toocms.tab.map.choosing.poi.near.ObtainNearPoiAty;
import com.toocms.tab.map.choosing.config.ChoosingConfig;
import com.toocms.tab.map.location.TooCMSLocationApi;
import com.toocms.tab.ui.BaseActivity;

/**
 * 地址选择Api封装
 * <p>
 * Author：Zero
 * Date：2018/6/23 15:45
 *
 * @version v1.0
 */
public class TooCMSChoosingApi {

    private volatile static TooCMSChoosingApi instance;

    private Context context;
    private PageOptions options;

    private TooCMSChoosingApi(Context context) {
        this.context = context;
        options = new PageOptions();
    }

    public static TooCMSChoosingApi getInstance(Context context) {
        if (instance == null)
            synchronized (TooCMSLocationApi.class) {
                if (instance == null)
                    instance = new TooCMSChoosingApi(context);
            }
        return instance;
    }

    /**
     * 配置页面选项
     *
     * @param options
     * @return
     */
    public TooCMSChoosingApi options(PageOptions options) {
        this.options = options;
        return this;
    }

    /**
     * 打开精确定位页并接收返回结果
     *
     * @param listener 定位结果回调监听
     */
    public void startPreciseLocationAty(LocationResultListener listener) {
        ChoosingConfig.locationResultListener = listener;
        startActivity(ObtainPreciseLocationAty.class);
    }

    /**
     * 打开附近poi页并接收返回结果
     *
     * @param listener 定位结果回调监听
     */
    public void startNearPoiAty(PoiResultListener listener) {
        ChoosingConfig.poiResultListener = listener;
        startActivity(ObtainNearPoiAty.class);
    }

    private void startActivity(Class<?> cls) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("options", options);
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).startActivity(cls, bundle);
        } else {
            Intent intent = new Intent(context, cls);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}

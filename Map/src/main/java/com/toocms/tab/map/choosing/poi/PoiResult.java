package com.toocms.tab.map.choosing.poi;

/**
 * poi返回结果
 * <p>
 * Author：Zero
 * Date：2020/6/17 14:28
 *
 * @version v1.0
 */

public class PoiResult {

    private String name;
    private String address;
    private String latitude;
    private String longitude;

    public PoiResult(String name, String address, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "PoiResult{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}

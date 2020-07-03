package com.toocms.tab.map.choosing.location;

/**
 * 位置选择结果
 * <p>
 * Author：Zero
 * Date：2020/6/16 19:11
 *
 * @version v1.0
 */

public class LocationResult {

    private String name;
    private String address;
    private String latitude;
    private String longitude;

    public LocationResult() {
    }

    public LocationResult(String name, String address, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationResult{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}

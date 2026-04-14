package com.jeyadevan.urlshortener.dto;

public class DeviceCount {
    private String deviceType;
    private long count;
    public DeviceCount(String deviceType, long count) {
        this.deviceType = deviceType;
        this.count = count;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public long getCount() {
        return count;
    }
}

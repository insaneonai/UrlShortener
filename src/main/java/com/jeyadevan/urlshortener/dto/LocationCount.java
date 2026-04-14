package com.jeyadevan.urlshortener.dto;

public class LocationCount {
    private String location;
    private long count;

    public LocationCount(String location, long count) {
        this.location = location;
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public long getCount() {
        return count;
    }    
}

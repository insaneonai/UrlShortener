package com.jeyadevan.urlshortener.dto;

public class FullUrl {
    private String originalUrl;

    public FullUrl() {
    }

    public FullUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}

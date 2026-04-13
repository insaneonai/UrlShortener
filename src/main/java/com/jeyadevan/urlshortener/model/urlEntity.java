package com.jeyadevan.urlshortener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "url")
public class urlEntity {
    @Id
    private String id;
    private String originalUrl;
    private String shortUrl;
    private Integer clickCount;

    public urlEntity() {

    }

    public urlEntity(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.clickCount = 0;
    }

    public String getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public String toString() {
        return "urlEntity{" +
                "id='" + id + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", clickCount=" + clickCount +
                '}';
    }
    
}

package com.jeyadevan.urlshortener.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;

@Document(collection = "clickEvent")
public class clickEventEntity {
    @Id
    private String id;

    private String urlId;
    
    @CreatedDate()
    private String timestamp;

    private String location;
    private String deviceType;

    public clickEventEntity() {

    }

    public clickEventEntity(String urlId, String location, String deviceType) {
        this.urlId = urlId;
        this.location = location;
        this.deviceType = deviceType;
    }

    public String getId() {
        return id;
    }

    public String getUrlId() {
        return urlId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String toString() {
        return "clickEventEntity{" +
                "id='" + id + '\'' +
                ", urlId='" + urlId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", location='" + location + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }

}

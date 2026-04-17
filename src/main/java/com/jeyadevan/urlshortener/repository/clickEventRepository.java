package com.jeyadevan.urlshortener.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.jeyadevan.urlshortener.model.clickEventEntity;
import com.jeyadevan.urlshortener.dto.LocationCount;
import com.jeyadevan.urlshortener.dto.DeviceCount;
import java.util.List;

public interface clickEventRepository extends MongoRepository<clickEventEntity, String> {
    List<clickEventEntity> findByUrlId(String urlId);

    // Aggregation query to get count by location
    @Aggregation(pipeline = {
        "{ $group: { _id: '$location', count: { $sum: 1 } } }"
    })
    List<LocationCount> countClicksByLocation();

    // Aggregation query to get count by device type
    @Aggregation(pipeline = {
        "{ $group: { _id: '$deviceType', count: { $sum: 1 } } }"
    })
    List<DeviceCount> countClicksByDeviceType();

    // Aggregation query to get count by location for specific urlId
    @Aggregation(pipeline = {
        "{ $match: { urlId: ?0 } }",
        "{ $group: { _id: '$location', count: { $sum: 1 } } }"
    })
    List<LocationCount> countClicksByLocationByUrlId(String urlId);

    // Aggregation query to get count by device type for specific urlId
    @Aggregation(pipeline = {
        "{ $match: { urlId: ?0 } }",
        "{ $group: { _id: '$deviceType', count: { $sum: 1 } } }"
    })
    List<DeviceCount> countClicksByDeviceTypeByUrlId(String urlId);

}

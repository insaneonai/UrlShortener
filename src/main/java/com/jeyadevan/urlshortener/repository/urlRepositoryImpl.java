package com.jeyadevan.urlshortener.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class urlRepositoryImpl implements urlRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public urlRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void incrementClickCount(String shortUrl) {
        Query query = new Query(Criteria.where("shortUrl").is(shortUrl));
        Update update = new Update().inc("clickCount", 1);
        mongoTemplate.updateFirst(query, update, com.jeyadevan.urlshortener.model.urlEntity.class);
    }
}
package com.jeyadevan.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jeyadevan.urlshortener.model.urlEntity;

public interface urlRepository extends MongoRepository<urlEntity, String>, urlRepositoryCustom {
    // Functional Requirements: Create a URL mapping, Retrive original URL, Track URL usage 
    urlEntity findByShortUrl(String shortUrl);

}

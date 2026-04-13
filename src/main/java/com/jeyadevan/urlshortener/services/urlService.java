package com.jeyadevan.urlshortener.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeyadevan.urlshortener.repository.urlRepository;
import com.jeyadevan.urlshortener.model.urlEntity;
import com.jeyadevan.urlshortener.dto.FullUrl;
import com.jeyadevan.urlshortener.dto.ShortUrl;
import com.jeyadevan.urlshortener.common.urlidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class urlService {
    Logger logger = LoggerFactory.getLogger(urlService.class);

    private final urlRepository urlRepository;
    private final urlidGenerator urlIdGenerator;

    @Autowired
    public urlService(urlRepository urlRepository,urlidGenerator urlIdGenerator){ 
        this.urlRepository = urlRepository;
        this.urlIdGenerator = urlIdGenerator;
    }

    public ShortUrl generateShortUrl(FullUrl fullUrl){
        String originalUrl = fullUrl.getOriginalUrl();

        // Hash the original URL to generate a unique short URL
        logger.info("Generating short URL for: {}", originalUrl);
        String shortUrl = urlIdGenerator.generateUniqueId();

        // Save the mapping to the database
        urlEntity urlEntity = new urlEntity(originalUrl, shortUrl);
        urlRepository.save(urlEntity);

        // Return the short URL to the client
        return new ShortUrl(shortUrl);
    }

    public FullUrl getFullUrl(String shortUrl){
        logger.debug("Retrieving original URL for short URL: {}", shortUrl);
        urlEntity urlEntity = urlRepository.findByShortUrl(shortUrl);
        if (urlEntity != null) {
            logger.debug("Original URL found: {}", urlEntity.getOriginalUrl());
            return new FullUrl(urlEntity.getOriginalUrl());
        } else {
            logger.warn("No original URL found for short URL: {}", shortUrl);
            return null;
        }
    }

    
}

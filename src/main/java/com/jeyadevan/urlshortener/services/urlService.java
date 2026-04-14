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
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class urlService {
    Logger logger = LoggerFactory.getLogger(urlService.class);

    private final urlRepository urlRepository;
    private final urlidGenerator urlIdGenerator;

    @Autowired
    public urlService(urlRepository urlRepository, urlidGenerator urlIdGenerator){ 
        this.urlRepository = urlRepository;
        this.urlIdGenerator = urlIdGenerator;
    }

    public ShortUrl generateShortUrl(FullUrl fullUrl){
        if (fullUrl == null || fullUrl.getOriginalUrl() == null || fullUrl.getOriginalUrl().isBlank()) {
            logger.warn("Invalid request: originalUrl is missing or empty");
            throw new IllegalArgumentException("originalUrl must be provided");
        }

        String originalUrl = fullUrl.getOriginalUrl().trim();
        if (!isValidUrl(originalUrl)) {
            logger.warn("Invalid URL received: {}", originalUrl);
            throw new IllegalArgumentException("originalUrl must be a valid HTTP/HTTPS URL");
        }

        logger.info("Generating short URL for: {}", originalUrl);
        String shortUrl = urlIdGenerator.generateUniqueId();

        urlEntity urlEntity = new urlEntity(originalUrl, shortUrl);
        urlRepository.save(urlEntity);

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

    private boolean isValidUrl(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            return scheme != null && host != null && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"));
        } catch (URISyntaxException e) {
            return false;
        }
    }
}


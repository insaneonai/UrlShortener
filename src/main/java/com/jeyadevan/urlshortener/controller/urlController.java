package com.jeyadevan.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.URISyntaxException;

import com.jeyadevan.urlshortener.dto.FullUrl;
import com.jeyadevan.urlshortener.dto.ShortUrl;
import com.jeyadevan.urlshortener.services.urlService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
public class urlController {
    Logger logger = LoggerFactory.getLogger(urlController.class);
    private final urlService urlService;

    @Autowired
    public urlController(urlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("URL Shortener is running!");
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrl> shortenUrl(@RequestBody FullUrl fullUrl) {
        if (fullUrl == null || fullUrl.getOriginalUrl() == null || fullUrl.getOriginalUrl().isBlank()) {
            logger.warn("Received invalid shorten request: missing originalUrl");
            return ResponseEntity.badRequest().build();
        }

        try {
            logger.info("Received request to shorten URL: {}", fullUrl.getOriginalUrl());
            ShortUrl shortUrl = urlService.generateShortUrl(fullUrl);
            logger.info("Generated short URL: {}", shortUrl.getShortUrl());
            return ResponseEntity.ok(shortUrl);
        } catch (IllegalArgumentException ex) {
            logger.warn("URL validation failed: {}", ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToFullUrl(@PathVariable String shortUrl){
        logger.debug("Received request to redirect short URL: {}", shortUrl);
        FullUrl fullUrl = urlService.getFullUrl(shortUrl);
        // Todo: Use a separate service for storing metadata like location, timestamp, click-count update etc. for analytics and monitoring purposes

        // Todo: Implement Redis caching for frequently accessed URLs to reduce database load and improve performance

        if (fullUrl != null) {
            logger.debug("Redirecting to original URL: {}", fullUrl.getOriginalUrl());
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(new URI(fullUrl.getOriginalUrl()));
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            } catch (URISyntaxException e) {
                logger.warn("Invalid original URL for short URL {}: {}", shortUrl, fullUrl.getOriginalUrl());
                return ResponseEntity.badRequest().build();
            }
        } else {
            logger.warn("No original URL found for short URL: {}", shortUrl);
            return ResponseEntity.notFound().build();
        }
    }


    
}
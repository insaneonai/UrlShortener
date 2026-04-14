package com.jeyadevan.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jeyadevan.urlshortener.model.urlEntity;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, urlEntity> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, urlEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Key serializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Value serializer as typed JSON
        JacksonJsonRedisSerializer<urlEntity> serializer = new JacksonJsonRedisSerializer<>(urlEntity.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}

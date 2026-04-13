package com.jeyadevan.urlshortener.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeUrlIdGenerator implements urlidGenerator {
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public SnowflakeUrlIdGenerator(@Value("${app.snowflake.machine-id:1}") long machineId){
        this.snowflakeIdGenerator = new SnowflakeIdGenerator(machineId);
    }

    @Override
    public String generateUniqueId(){
        return Base62Util.encode(snowflakeIdGenerator.nextId());
    }
}

package com.EnterprisePlatform.repository.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SqlLoader {
    private static final Logger log = LoggerFactory.getLogger(SqlLoader.class);
    private final Map<SqlKey,String> cache = new ConcurrentHashMap<>();

    public void load(SqlKey key,String path)  {
        log.info("Key {} Value {}",key,path);
        cache.put(key,read(path));
    }

    private String read(String path)  {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)){
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new RuntimeException("Failed to load SQL: " + path,e);
        }
    }

    public  String get(SqlKey key){
        String sql = cache.get(key);

        if(sql==null){
            throw new IllegalStateException("SQL not found: " + key);
        }

        return sql;
    }

}
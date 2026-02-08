package com.EnterprisePlatform.repository.support;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SqlRegistry {
    private final SqlLoader loader;

    public SqlRegistry(SqlLoader loader){
        this.loader = loader;
    }


    @PostConstruct
    void register(){
        for(SqlKey key :SqlKey.values()) {
            loader.load(key, key.getKey());
        }
        }

    public String get(SqlKey key){
        return loader.get(key);
    }

}

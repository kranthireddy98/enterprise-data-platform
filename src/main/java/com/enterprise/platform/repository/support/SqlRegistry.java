package com.enterprise.platform.repository.support;


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
        loader.load(SqlKey.CUSTOMER_FETCH_BY_ID, SqlKey.CUSTOMER_FETCH_BY_ID.getKey());
        loader.load(SqlKey.CUSTOMER_FETCH_ALL,SqlKey.CUSTOMER_FETCH_ALL.getKey());
    }

    public String get(SqlKey key){
        return loader.get(key);
    }

}

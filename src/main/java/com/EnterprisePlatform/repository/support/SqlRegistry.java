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
        loader.load(SqlKey.CUSTOMER_FETCH_BY_ID, SqlKey.CUSTOMER_FETCH_BY_ID.getKey());
        loader.load(SqlKey.CUSTOMER_FETCH_ALL,SqlKey.CUSTOMER_FETCH_ALL.getKey());
        loader.load(SqlKey.CUSTOMER_INSERT,SqlKey.CUSTOMER_INSERT.getKey());


        loader.load(SqlKey.COUNTRY_FETCH_ACTIVE_BY_CODE,SqlKey.COUNTRY_FETCH_ACTIVE_BY_CODE.getKey());
    }

    public String get(SqlKey key){
        return loader.get(key);
    }

}

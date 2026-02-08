package com.EnterprisePlatform.infrastructure.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.EnumMap;
import java.util.Map;

@Component
public class DataSourceManager {

    private final Map<DataSourceType, HikariDataSource> cache = new EnumMap<>(DataSourceType.class);

    public synchronized DataSource getOrCreate(DataSourceType type,HikariConfig config){
        return cache.computeIfAbsent(
                type,t->new HikariDataSource(config)
        );
    }

    @PreDestroy
    public void shutdown(){
        cache.values().forEach(HikariDataSource::close);
    }

}

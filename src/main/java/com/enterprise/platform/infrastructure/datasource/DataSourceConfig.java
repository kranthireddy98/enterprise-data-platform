package com.enterprise.platform.infrastructure.datasource;

import com.zaxxer.hikari.HikariConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class DataSourceConfig {
    private final HikariConfigFactory factory;
    private final DataSourceManager manager;

    @Bean("appDataSource")
    public DataSource appDataSource() {
        HikariConfig config = factory.create(DataSourceType.APPLICATION);
        return manager.getOrCreate(DataSourceType.APPLICATION,config);
    }

    @Bean("masterDataSource")
    public DataSource masterDataSource() {
        HikariConfig config = factory.create(DataSourceType.MASTER);
        return manager.getOrCreate(DataSourceType.MASTER,config);
    }

}



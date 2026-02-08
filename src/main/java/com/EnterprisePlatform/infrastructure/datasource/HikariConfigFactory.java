package com.EnterprisePlatform.infrastructure.datasource;

import com.EnterprisePlatform.config.profile.DbProperties;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.stereotype.Component;

@Component
public class HikariConfigFactory {

    private final DbProperties props;

    public HikariConfigFactory(DbProperties props) {
        this.props = props;
    }

    public HikariConfig create (DataSourceType type){
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
                type==DataSourceType.MASTER ? masterJdbcUrl() :buildJdbcUrl());

        config.setUsername(props.getUsername());
        config.setPassword(props.getPassword());

        config.setMaximumPoolSize(props.getPool().getMaxSize());
        config.setMinimumIdle(props.getPool().getMinIdle());

        config.setPoolName("enterprise-platform-"+type.name().toLowerCase());

        //safe defaults
        config.setConnectionTimeout(30_000);
        config.setIdleTimeout(600_000);
        config.setMaxLifetime(1_000_000);

        return config;
    }

    private String masterJdbcUrl() {
        return "jdbc:sqlserver://"
                + props.getHost() + ":" + props.getPort()
                + ";databaseName=master"
                + ";encrypt=true;trustServerCertificate=true";
    }

    private String buildJdbcUrl() {
        return "jdbc:sqlserver://"
                + props.getHost() + ":" + props.getPort()
                + ";databaseName=" + props.getDatabase()
                + ";encrypt=true;trustServerCertificate=true";
    }


}

package com.enterprise.platform.runner;

import com.enterprise.platform.infrastructure.datasource.DataSourceManager;
import com.enterprise.platform.infrastructure.datasource.DataSourceType;
import com.enterprise.platform.infrastructure.datasource.HikariConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
@Component
public class StartupDbTestRunner implements CommandLineRunner {

    private final HikariConfigFactory configFactory;
    private final DataSourceManager dataSourceManager;

    public StartupDbTestRunner(HikariConfigFactory configFactory, DataSourceManager dataSourceManager) {
        this.configFactory = configFactory;
        this.dataSourceManager = dataSourceManager;
    }

    @Override
    public void run(String... args) throws Exception {
        DataSource dataSource = dataSourceManager.getOrCreate(
                DataSourceType.APPLICATION,
                configFactory.create(DataSourceType.APPLICATION));

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT 1");
             ResultSet rs = ps.executeQuery()
        ){
            log.info("Connected DB: {}", conn.getCatalog());
            while (rs.next())
            {
                System.out.println("DB Connected -> " + rs.getInt(1));
            }
        }
    }
}

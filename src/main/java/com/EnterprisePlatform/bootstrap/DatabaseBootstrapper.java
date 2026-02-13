package com.EnterprisePlatform.bootstrap;

import com.EnterprisePlatform.datasource.DataSourceManager;
import com.EnterprisePlatform.datasource.DataSourceType;
import com.EnterprisePlatform.datasource.HikariConfigFactory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class DatabaseBootstrapper {

    private static final Logger log = LoggerFactory.getLogger(DatabaseBootstrapper.class);


    private final HikariConfigFactory configFactory;
    private final DataSourceManager dataSourceManager;

    public DatabaseBootstrapper(HikariConfigFactory configFactory, DataSourceManager dataSourceManager) {
        this.configFactory = configFactory;
        this.dataSourceManager = dataSourceManager;
    }

    @PostConstruct
    public void bootstrap(){

        try {
            log.info("Starting database bootstrap");

            DataSource masterDs = dataSourceManager.getOrCreate(
                    DataSourceType.MASTER,
                    configFactory.create(DataSourceType.MASTER));


            createDatabase(masterDs);

            DataSource appDs  = dataSourceManager.getOrCreate(
                    DataSourceType.APPLICATION,
                    configFactory.create(DataSourceType.APPLICATION));

            createSchema(appDs );
            createTables(appDs );

            log.info("Database bootstrap completed successfully");
        } catch (Exception e) {
            log.error("Database bootstrap failed: {}",e.toString());
            throw new IllegalStateException("Database bootstrap failed",e);
        }
    }

    private void createTables(DataSource ds) throws Exception {
        executeSql(ds, "sql/tables/master_tables.sql");
        executeSql(ds, "sql/tables/core_tables.sql");
        executeSql(ds, "sql/tables/ops_tables.sql");

        executeSql(ds, "sql/data/master_seed.sql");
    }

    private void createSchema(DataSource ds) throws Exception {

        executeSql(ds, "sql/schema/create_schemas.sql");
    }

    private void createDatabase(DataSource ds) throws Exception {
        executeSql(ds,"sql/database/create_database.sql");
    }

    private void executeSql(DataSource ds,String s) throws Exception {
        try
            (var is = getClass().getClassLoader().getResourceAsStream(s);

            var con = ds.getConnection();

            var stmt = con.createStatement()){
            if(is == null){
                throw new IllegalStateException("SQL file is not found: "+ s);
            }
            String sql = new String(is.readAllBytes());
            stmt.execute(sql);
        }
    }


}

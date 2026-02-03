package com.enterprise.platform.repository.support;

import com.enterprise.platform.infrastructure.datasource.DataSourceManager;
import com.enterprise.platform.infrastructure.datasource.DataSourceType;
import com.enterprise.platform.infrastructure.datasource.HikariConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;


@Component
public class TransactionManager {

    private final DataSourceManager dataSourceManager;
    private final HikariConfigFactory configFactory;
    private static final Logger log = LoggerFactory.getLogger(TransactionManager.class);

    public TransactionManager(DataSourceManager dataSourceManager, HikariConfigFactory configFactory) {
        this.dataSourceManager = dataSourceManager;
        this.configFactory = configFactory;
    }

    public <T> T execute(TransactionCallback<T> callback){

        DataSource ds = dataSourceManager.getOrCreate(DataSourceType.APPLICATION,
                configFactory.create(DataSourceType.APPLICATION));

        try(Connection con = ds.getConnection()) {

            try {
                con.setAutoCommit(false);

                T result = callback.doInTransaction(con);

                con.commit();
                return result;
            }catch (Exception ex){
                con.rollback();
                throw ex;
            }
        } catch (Exception e) {
            log.error("Transaction failed with root cause : {}", e.toString());
            throw new RuntimeException("Transaction failed",e);
        }
    }
}

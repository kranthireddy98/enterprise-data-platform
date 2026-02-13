package com.EnterprisePlatform.transaction;

import com.EnterprisePlatform.infrastructure.datasource.DataSourceManager;
import com.EnterprisePlatform.infrastructure.datasource.DataSourceType;
import com.EnterprisePlatform.infrastructure.datasource.HikariConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;


@Component
public class TransactionManager {

    private static final Logger log = LoggerFactory.getLogger(TransactionManager.class);
    private final DataSourceManager dataSourceManager;
    private final HikariConfigFactory configFactory;

    public  TransactionManager(DataSourceManager dataSourceManager, HikariConfigFactory configFactory){

        this.dataSourceManager=dataSourceManager;
        this.configFactory=configFactory;

    }

    public <T> T execute(TransactionCallback<T> callback) throws Exception {

        DataSource ds = dataSourceManager.getOrCreate(
                DataSourceType.APPLICATION,
                configFactory.create(DataSourceType.APPLICATION)
        );

        try(Connection con = ds.getConnection()){
            try {
                con.setAutoCommit(false);

                T result = callback.execute(con);

                con.commit();
                return result;
            }catch (Exception ex){
                log.error("Transaction Failed",ex);
                con.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}


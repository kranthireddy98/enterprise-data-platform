package com.EnterprisePlatform.transaction;

import com.EnterprisePlatform.infrastructure.datasource.DataSourceManager;
import com.EnterprisePlatform.infrastructure.datasource.DataSourceType;
import com.EnterprisePlatform.infrastructure.datasource.HikariConfigFactory;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;

@Component
public class TransactionManager {

    private final DataSourceManager dataSourceManager;
    private final HikariConfigFactory configFactory;

    public  TransactionManager(DataSourceManager dataSourceManager, HikariConfigFactory configFactory){

        this.dataSourceManager=dataSourceManager;
        this.configFactory=configFactory;

    }

    public <T> T execute(TransactionCallback<T> callback){

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
                con.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw new RuntimeException("Transaction failed",e);
        }
    }

}


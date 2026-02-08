package com.EnterprisePlatform.repository.command;

import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.Map;

@Repository
public class AddressCommandRepository {
    private final JdbcExecutor executor;
    private final SqlRegistry sqlRegistry;

    public  AddressCommandRepository(JdbcExecutor executor, SqlRegistry registry){
        this.executor=executor;
        this.sqlRegistry = registry;
    }

    public int deleteByCustomerId(Connection con, Long customerId){
        return executor.update(
                con,
                sqlRegistry.get(SqlKey.ADDRESS_DELETE_BY_CUSTOMER),
                Map.of("customerId",customerId)
        );
    }


}

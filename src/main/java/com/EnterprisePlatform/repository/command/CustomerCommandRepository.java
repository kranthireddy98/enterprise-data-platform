package com.EnterprisePlatform.repository.command;

import com.EnterprisePlatform.repository.support.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.Map;

@Repository
public class CustomerCommandRepository {
    private final SqlRegistry sqls;
    private final JdbcExecutor jdbc;


    public CustomerCommandRepository(SqlRegistry sqls, JdbcExecutor jdbc) {
        this.sqls = sqls;
        this.jdbc = jdbc;
    }

    public int insert(Connection con, Map<String,Object> params){
        return jdbc.update(con,
                sqls.get(SqlKey.CUSTOMER_INSERT),
               params );
    }

    public int delete(Connection con, Long customerId){
        return jdbc.update(
                con,
                sqls.get(SqlKey.CUSTOMER_DELETE),
                Map.of("customerId",customerId)
        );
    }
}
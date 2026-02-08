package com.EnterprisePlatform.repository.query;

import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerQueryRepository {

    private final SqlRegistry sqls;
    private final JdbcExecutor jdbc;

    public CustomerQueryRepository(SqlRegistry sqls, JdbcExecutor jdbc) {
        this.sqls = sqls;
        this.jdbc = jdbc;
    }


    public List<Map<String, Object>> fetchAll() {
        return jdbc.query(
            sqls.get(SqlKey.CUSTOMER_FETCH_ALL),
            Map.of()
        );
    }

    public List<Map<String, Object>> fetchById(Long id) {
        return jdbc.query(
            sqls.get(SqlKey.CUSTOMER_FETCH_BY_ID),
            Map.of("customerId", id)
        );
    }
}

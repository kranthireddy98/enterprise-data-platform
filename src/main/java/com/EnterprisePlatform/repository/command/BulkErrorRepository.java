package com.EnterprisePlatform.repository.command;

import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BulkErrorRepository {

    private final JdbcExecutor jdbc;
    private final SqlRegistry sqlRegistry;

    public BulkErrorRepository(JdbcExecutor jdbc, SqlRegistry sqlRegistry) {
        this.jdbc = jdbc;
        this.sqlRegistry = sqlRegistry;
    }

    public void recordError(
            Long rowId,
            String errorCode,
            String message
    ) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("rowId", rowId);
        params.put("errorCode", errorCode);
        params.put("errorMessage", message);

        jdbc.update(
                sqlRegistry.get(SqlKey.BULK_ERROR_INSERT),
                params
        );
    }
}
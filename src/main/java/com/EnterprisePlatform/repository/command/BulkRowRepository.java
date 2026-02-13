package com.EnterprisePlatform.repository.command;

import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BulkRowRepository {

    private final JdbcExecutor jdbc;
    private final SqlRegistry sqlRegistry;


    public BulkRowRepository(JdbcExecutor jdbc, SqlRegistry sqlRegistry) {
        this.jdbc = jdbc;
        this.sqlRegistry = sqlRegistry;
    }

    public Long createRow(long jobId, int rowNumber){

        Map<String,Object> params = new HashMap<>();
        params.put("jobId",jobId);
        params.put("rowNumber",rowNumber);
        params.put("status","IN_PROGRESS");
        return jdbc.query(
                sqlRegistry.get(SqlKey.BULK_ROW_CREATE),
               params
        ).stream()
                .map(r -> ((Number) r.get("row_id")).longValue())
                .findFirst()
                .orElseThrow();

    }

    public void markSuccess(Long rowId){
        jdbc.update(
                sqlRegistry.get(SqlKey.BULK_ROW_MARK_SUCCESS),
                Map.of("rowId",rowId)
        );

    }

    public void markFailed(Long rowId){
        jdbc.update(
                sqlRegistry.get(SqlKey.BULK_ROW_MARK_FAILED),
                Map.of("rowId",rowId)
        );
    }

}

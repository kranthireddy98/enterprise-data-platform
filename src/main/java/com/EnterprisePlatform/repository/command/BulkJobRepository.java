package com.EnterprisePlatform.repository.command;

import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BulkJobRepository {

    private final JdbcExecutor jdbc;
    private final SqlRegistry sqlRegistry;

    public BulkJobRepository(JdbcExecutor jdbc, SqlRegistry sqlRegistry) {
        this.jdbc = jdbc;
        this.sqlRegistry = sqlRegistry;
    }

    public Long createJob(String type, String uploadedBy, int totalRows){
        Map<String,Object> params = new HashMap<>();
        params.put("jobType", type);
        params.put("status","IN_PROGRESS");
        params.put("totalRows",totalRows);
        params.put("uploadedBy",uploadedBy);
        params.put("startedAt",LocalDateTime.now());

       return jdbc.query(
                sqlRegistry.get(SqlKey.BULK_JOB_CREATE),
                params
        ).stream()
                .map(r -> ((Number) r.get("job_id")).longValue())
                .findFirst()
                .orElseThrow();

    }

    public void completeJob(Long jobId,
                            int successRows,
                            int failRows,
                            LocalDateTime completedAt){
        Map<String,Object> params = new HashMap<>();
        params.put("successRows", successRows);
        params.put("failedRows",failRows);
        params.put("completedAt",completedAt);
        params.put("jobId",jobId);
        jdbc.update(
                sqlRegistry.get(SqlKey.BULK_JOB_COMPLETE),
               params
        );
    }
}

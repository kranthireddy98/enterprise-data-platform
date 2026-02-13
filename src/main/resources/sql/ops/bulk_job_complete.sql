UPDATE ops.bulk_import_job
SET success_rows = :successRows,
    failed_rows = :failedRows,
    status = 'COMPLETED',
    completed_at = :completedAt
WHERE job_id = :jobId;

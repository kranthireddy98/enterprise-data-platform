INSERT INTO ops.bulk_import_job (
    job_type,
    status,
    total_rows,
    success_rows,
    failed_rows,
    uploaded_by,
    started_at
)
OUTPUT INSERTED.job_id
VALUES (
           :jobType,
           :status,
           :totalRows,
           0,
           0,
           :uploadedBy,
           :startedAt
       );

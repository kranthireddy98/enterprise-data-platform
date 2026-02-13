INSERT INTO ops.bulk_import_row (
    job_id,
    row_number,
    status
)
OUTPUT INSERTED.row_id
VALUES (
           :jobId,
           :rowNumber,
           :status
       );

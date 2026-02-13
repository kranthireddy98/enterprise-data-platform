UPDATE ops.bulk_import_row
SET status = 'FAILED'
WHERE row_id = :rowId;

UPDATE ops.bulk_import_row
SET status = 'SUCCESS'
WHERE row_id = :rowId;

INSERT INTO ops.bulk_import_error (
    row_id,
    error_code,
    error_message
)
VALUES (
           :rowId,
           :errorCode,
           :errorMessage
       );

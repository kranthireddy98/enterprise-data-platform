IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'bulk_import_job' AND schema_id = SCHEMA_ID('ops')
)
BEGIN
CREATE TABLE ops.bulk_import_job (
                                     job_id BIGINT IDENTITY PRIMARY KEY,
                                     job_type VARCHAR(50) NOT NULL,
                                     status VARCHAR(30) NOT NULL,

                                     total_rows INT,
                                     success_rows INT,
                                     failed_rows INT,

                                     uploaded_by VARCHAR(100),
                                     started_at DATETIME2,
                                     completed_at DATETIME2
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'bulk_import_row' AND schema_id = SCHEMA_ID('ops')
)
BEGIN
CREATE TABLE ops.bulk_import_row (
                                     row_id BIGINT IDENTITY PRIMARY KEY,
                                     job_id BIGINT NOT NULL,
                                     row_number INT NOT NULL,
                                     business_key VARCHAR(100),
                                     status VARCHAR(30),

                                     CONSTRAINT ux_job_row UNIQUE (job_id, row_number),

                                     FOREIGN KEY (job_id)
                                         REFERENCES ops.bulk_import_job(job_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'bulk_import_row_payload' AND schema_id = SCHEMA_ID('ops')
)
BEGIN
CREATE TABLE ops.bulk_import_row_payload (
                                             payload_id BIGINT IDENTITY PRIMARY KEY,
                                             row_id BIGINT NOT NULL UNIQUE,
                                             raw_payload NVARCHAR(MAX),

                                             FOREIGN KEY (row_id)
                                                 REFERENCES ops.bulk_import_row(row_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'bulk_import_error' AND schema_id = SCHEMA_ID('ops')
)
BEGIN
CREATE TABLE ops.bulk_import_error (
                                       error_id BIGINT IDENTITY PRIMARY KEY,
                                       row_id BIGINT NOT NULL,
                                       field_name VARCHAR(50),
                                       error_code VARCHAR(50),
                                       error_message VARCHAR(200),

                                       FOREIGN KEY (row_id)
                                           REFERENCES ops.bulk_import_row(row_id)
);
END;


IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_country' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_country (
                                   country_id BIGINT IDENTITY PRIMARY KEY,
                                   country_code VARCHAR(10) NOT NULL,
                                   country_name VARCHAR(100) NOT NULL,

                                   effective_start_date DATE NOT NULL,
                                   effective_end_date DATE NULL,
                                   is_active BIT NOT NULL DEFAULT 1,

                                   created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);

CREATE UNIQUE INDEX ux_country_code_eff
    ON master.md_country(country_code, effective_start_date);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_state' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_state (
                                 state_id BIGINT IDENTITY PRIMARY KEY,
                                 state_code VARCHAR(10) NOT NULL,
                                 state_name VARCHAR(100) NOT NULL,
                                 country_id BIGINT NOT NULL,

                                 effective_start_date DATE NOT NULL,
                                 effective_end_date DATE NULL,
                                 is_active BIT DEFAULT 1,

                                 FOREIGN KEY (country_id)
                                     REFERENCES master.md_country(country_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_currency' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_currency (
                                    currency_id BIGINT IDENTITY PRIMARY KEY,
                                    currency_code VARCHAR(10) NOT NULL,
                                    currency_name VARCHAR(50),

                                    effective_start_date DATE NOT NULL,
                                    effective_end_date DATE NULL,
                                    is_active BIT DEFAULT 1
);

CREATE UNIQUE INDEX ux_currency_code_eff
    ON master.md_currency(currency_code, effective_start_date);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_department' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_department (
    department_id BIGINT IDENTITY PRIMARY KEY,
    department_code VARCHAR(20) NOT NULL,
    department_name VARCHAR(100),
    effective_start_date DATE NOT NULL,
    effective_end_date DATE NULL,
    is_active BIT DEFAULT 1
    );
END;


IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_product' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_product (
                                   product_id BIGINT IDENTITY PRIMARY KEY,
                                   product_code VARCHAR(50) NOT NULL,
                                   product_name VARCHAR(200),
                                   base_price DECIMAL(12,2),

                                   effective_start_date DATE NOT NULL,
                                   effective_end_date DATE NULL,
                                   is_active BIT DEFAULT 1
);

CREATE UNIQUE INDEX ux_product_code_eff
    ON master.md_product(product_code, effective_start_date);
END;


IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_location' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_location (
    location_id BIGINT IDENTITY PRIMARY KEY,
    location_code VARCHAR(20) NOT NULL,
    location_name VARCHAR(100),
    country_id BIGINT NOT NULL,
    effective_start_date DATE NOT NULL,
    effective_end_date DATE NULL,
    is_active BIT DEFAULT 1,
    FOREIGN KEY (country_id) REFERENCES master.md_country(country_id)
    );
END;


IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_order_status' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_order_status (
    status_id BIGINT IDENTITY PRIMARY KEY,
    status_code VARCHAR(20) NOT NULL,
    status_name VARCHAR(50) );
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_payment_method' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_payment_method (
    payment_method_id BIGINT IDENTITY PRIMARY KEY,
    method_code VARCHAR(20) NOT NULL,
    method_name VARCHAR(50) );
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'md_tax_code' AND schema_id = SCHEMA_ID('master')
)
BEGIN
CREATE TABLE master.md_tax_code (
    tax_code_id BIGINT IDENTITY PRIMARY KEY,
    tax_code VARCHAR(20) NOT NULL,
    percentage DECIMAL(5,2),
    effective_start_date DATE NOT NULL,
    effective_end_date DATE NULL,
    is_active BIT DEFAULT 1 );
END;
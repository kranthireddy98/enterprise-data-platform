IF NOT EXISTS (
    SELECT 1 FROM master.md_country WHERE country_code = 'IN'
)
BEGIN
INSERT INTO master.md_country (
    country_code,
    country_name,
    effective_start_date,
    effective_end_date,
    is_active
)
VALUES
    ('IN', 'India', '2000-01-01', NULL, 1),
    ('US', 'United States', '2000-01-01', NULL, 1);
END;


IF NOT EXISTS (
    SELECT 1 FROM master.md_state WHERE state_code = 'KA'
)
BEGIN
INSERT INTO master.md_state (
    state_code,
    state_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
SELECT
    'KA',
    'Karnataka',
    country_id,
    '2000-01-01',
    NULL,
    1
FROM master.md_country
WHERE country_code = 'IN';
END;


IF NOT EXISTS (
    SELECT 1 FROM master.md_state WHERE state_code = 'KA'
)
BEGIN
INSERT INTO master.md_state (
    state_code,
    state_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
SELECT
    'KA',
    'Karnataka',
    country_id,
    '2000-01-01',
    NULL,
    1
FROM master.md_country
WHERE country_code = 'IN';
END;

IF NOT EXISTS (
    SELECT 1 FROM master.md_state WHERE state_code = 'KA'
)
BEGIN
INSERT INTO master.md_state (
    state_code,
    state_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
SELECT
    'KA',
    'Karnataka',
    country_id,
    '2000-01-01',
    NULL,
    1
FROM master.md_country
WHERE country_code = 'IN';
END;


IF NOT EXISTS (
    SELECT 1 FROM master.md_state WHERE state_code = 'TS'
)
BEGIN
INSERT INTO master.md_state (
    state_code,
    state_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
SELECT
    'TS',
    'Telangana',
    country_id,
    '2000-01-01',
    NULL,
    1
FROM master.md_country
WHERE country_code = 'IN';
END;

IF NOT EXISTS (
    SELECT 1 FROM master.md_department WHERE department_code = 'SALES'
)
BEGIN
INSERT INTO master.md_department (
    department_code,
    department_name,
    effective_start_date,
    effective_end_date,
    is_active
)
VALUES
    ('SALES', 'Sales', '2000-01-01', NULL, 1),
    ('FIN', 'Finance', '2000-01-01', NULL, 1),
    ('OPS', 'Operations', '2000-01-01', NULL, 1);
END;


IF NOT EXISTS (
    SELECT 1 FROM master.md_location WHERE location_code = 'BLR'
)
BEGIN
INSERT INTO master.md_location (
    location_code,
    location_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
SELECT
    'BLR',
    'Bangalore',
    country_id,
    '2000-01-01',
    NULL,
    1
FROM master.md_country
WHERE country_code = 'IN';
END;

IF NOT EXISTS (
    SELECT 1 FROM master.md_location WHERE location_code = 'BLR'
)
BEGIN
INSERT INTO master.md_location (
    location_code,
    location_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
SELECT
    'BLR',
    'Bangalore',
    country_id,
    '2000-01-01',
    NULL,
    1
FROM master.md_country
WHERE country_code = 'IN';
END;

IF NOT EXISTS (
    SELECT 1 FROM master.md_product WHERE product_code = 'PROD-A'
)
BEGIN
INSERT INTO master.md_product (
    product_code,
    product_name,
    base_price,
    effective_start_date,
    effective_end_date,
    is_active
)
VALUES
    ('PROD-A', 'Laptop', 55000.00, '2000-01-01', NULL, 1),
    ('PROD-B', 'Keyboard', 1500.00, '2000-01-01', NULL, 1),
    ('PROD-C', 'Mouse', 800.00, '2000-01-01', NULL, 1);
END;


IF NOT EXISTS (
    SELECT 1 FROM master.md_order_status WHERE status_code = 'CREATED'
)
BEGIN
INSERT INTO master.md_order_status (
    status_code,
    status_name
)
VALUES
    ('CREATED', 'Created'),
    ('PAID', 'Paid'),
    ('SHIPPED', 'Shipped'),
    ('CANCELLED', 'Cancelled');
END;

IF NOT EXISTS (
    SELECT 1 FROM master.md_payment_method WHERE method_code = 'CARD'
)
BEGIN
INSERT INTO master.md_payment_method (
    method_code,
    method_name
)
VALUES
    ('CARD', 'Credit / Debit Card'),
    ('BANK', 'Bank Transfer'),
    ('CASH', 'Cash');
END;

IF NOT EXISTS (
    SELECT 1 FROM master.md_tax_code WHERE tax_code = 'GST18'
)
BEGIN
INSERT INTO master.md_tax_code (
    tax_code,
    percentage,
    effective_start_date,
    effective_end_date,
    is_active
)
VALUES
    ('GST18', 18.00, '2000-01-01', NULL, 1),
    ('GST12', 12.00, '2000-01-01', NULL, 1);
END;

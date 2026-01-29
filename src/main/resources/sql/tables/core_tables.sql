IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'customer' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.customer (
                               customer_id BIGINT IDENTITY PRIMARY KEY,
                               customer_number VARCHAR(50) UNIQUE NOT NULL,
                               full_name VARCHAR(200) NOT NULL,
                               country_id BIGINT NOT NULL,

                               effective_start_date DATE NOT NULL,
                               effective_end_date DATE NULL,
                               is_active BIT DEFAULT 1,

                               created_at DATETIME2 DEFAULT SYSUTCDATETIME(),

                               CONSTRAINT fk_customer_country
                                   FOREIGN KEY (country_id)
                                       REFERENCES master.md_country(country_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'customer_address' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.customer_address (
                                       address_id BIGINT IDENTITY PRIMARY KEY,
                                       customer_id BIGINT NOT NULL,
                                       state_id BIGINT NOT NULL,
                                       city VARCHAR(100),

                                       effective_start_date DATE NOT NULL,
                                       effective_end_date DATE NULL,
                                       is_active BIT DEFAULT 1,

                                       FOREIGN KEY (customer_id)
                                           REFERENCES core.customer(customer_id),
                                       FOREIGN KEY (state_id)
                                           REFERENCES master.md_state(state_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'customer_contract' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.customer_contract (
                                        contract_id BIGINT IDENTITY PRIMARY KEY,
                                        customer_id BIGINT NOT NULL,
                                        contract_code VARCHAR(50) NOT NULL,

                                        effective_start_date DATE NOT NULL,
                                        effective_end_date DATE NULL,
                                        is_active BIT DEFAULT 1,

                                        FOREIGN KEY (customer_id)
                                            REFERENCES core.customer(customer_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'customer_order' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.customer_order (
                                     order_id BIGINT IDENTITY PRIMARY KEY,
                                     customer_id BIGINT NOT NULL,
                                     contract_id BIGINT NOT NULL,
                                     status_id BIGINT NOT NULL,
                                     order_date DATE NOT NULL,

                                     FOREIGN KEY (customer_id)
                                         REFERENCES core.customer(customer_id),
                                     FOREIGN KEY (contract_id)
                                         REFERENCES core.customer_contract(contract_id),
                                     FOREIGN KEY (status_id)
                                         REFERENCES master.md_order_status(status_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'contract_product_price' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.contract_product_price (
                                             contract_id BIGINT NOT NULL,
                                             product_id BIGINT NOT NULL,
                                             price DECIMAL(12,2) NOT NULL,

                                             effective_start_date DATE NOT NULL,
                                             effective_end_date DATE NULL,
                                             is_active BIT DEFAULT 1,

                                             PRIMARY KEY (contract_id, product_id, effective_start_date),

                                             FOREIGN KEY (contract_id)
                                                 REFERENCES core.customer_contract(contract_id),
                                             FOREIGN KEY (product_id)
                                                 REFERENCES master.md_product(product_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'customer_billing_account' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.customer_billing_account (
                                               billing_account_id BIGINT IDENTITY PRIMARY KEY,
                                               customer_id BIGINT NOT NULL,
                                               currency_id BIGINT NOT NULL,
                                               payment_method_id BIGINT NOT NULL,

                                               is_active BIT DEFAULT 1,

                                               FOREIGN KEY (customer_id)
                                                   REFERENCES core.customer(customer_id),
                                               FOREIGN KEY (currency_id)
                                                   REFERENCES master.md_currency(currency_id),
                                               FOREIGN KEY (payment_method_id)
                                                   REFERENCES master.md_payment_method(payment_method_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'order_item' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.order_item (
                                 order_item_id BIGINT IDENTITY PRIMARY KEY,
                                 order_id BIGINT NOT NULL,
                                 product_id BIGINT NOT NULL,
                                 quantity INT NOT NULL,

                                 FOREIGN KEY (order_id)
                                     REFERENCES core.customer_order(order_id),
                                 FOREIGN KEY (product_id)
                                     REFERENCES master.md_product(product_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'invoice' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.invoice (
                              invoice_id BIGINT IDENTITY PRIMARY KEY,
                              customer_id BIGINT NOT NULL,
                              billing_account_id BIGINT NOT NULL,
                              invoice_date DATE NOT NULL,
                              total_amount DECIMAL(14,2),

                              FOREIGN KEY (customer_id)
                                  REFERENCES core.customer(customer_id),
                              FOREIGN KEY (billing_account_id)
                                  REFERENCES core.customer_billing_account(billing_account_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'invoice_line' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.invoice_line (
                                   invoice_line_id BIGINT IDENTITY PRIMARY KEY,
                                   invoice_id BIGINT NOT NULL,
                                   product_id BIGINT NOT NULL,
                                   amount DECIMAL(12,2),

                                   FOREIGN KEY (invoice_id)
                                       REFERENCES core.invoice(invoice_id),
                                   FOREIGN KEY (product_id)
                                       REFERENCES master.md_product(product_id)
);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.tables
    WHERE name = 'order_payment' AND schema_id = SCHEMA_ID('core')
)
BEGIN
CREATE TABLE core.order_payment (
                                    payment_id BIGINT IDENTITY PRIMARY KEY,
                                    order_id BIGINT NOT NULL,
                                    payment_method_id BIGINT NOT NULL,
                                    amount DECIMAL(12,2),

                                    FOREIGN KEY (order_id)
                                        REFERENCES core.customer_order(order_id),
                                    FOREIGN KEY (payment_method_id)
                                        REFERENCES master.md_payment_method(payment_method_id)
);
END;


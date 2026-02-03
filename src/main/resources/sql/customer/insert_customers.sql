INSERT INTO core.customer (
    customer_number,
    full_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
VALUES (
           :customerNumber,
           :fullName,
           :countryId,
           :effectiveStartDate,
           NULL,
           1
       );

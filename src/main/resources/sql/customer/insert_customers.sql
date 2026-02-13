INSERT INTO core.customer (
    customer_number,
    full_name,
    country_id,
    effective_start_date,
    effective_end_date,
    is_active
)
    OUTPUT INSERTED.customer_id
VALUES (
           :customerNumber,
           :fullName,
           :countryId,
           :startDate,
           :endDate,
           1
       );

SELECT currency_id as id, currency_code as code
FROM master.md_currency
WHERE currency_code = :code
  AND is_active = 1
  AND effective_start_date <= :asOfDate
  AND (effective_end_date IS NULL OR effective_end_date >= :asOfDate );

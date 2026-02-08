SELECT product_id as id, product_code as code
FROM master.md_product
WHERE product_code = :code
  AND is_active = 1
  AND effective_start_date <= :asOfDate
  AND (effective_end_date IS NULL OR effective_end_date >= :asOfDate );

SELECT tax_code_id as id, tax_code as code
FROM master.md_tax_code
WHERE tax_code = :code
  AND is_active = 1
  AND effective_start_date <= :asOfDate
  AND (effective_end_date IS NULL OR effective_end_date >= :asOfDate );

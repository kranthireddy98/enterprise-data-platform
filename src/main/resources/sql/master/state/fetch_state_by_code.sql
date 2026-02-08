SELECT state_id as id, state_code as code
FROM master.md_state
WHERE state_code = :code
  AND is_active = 1
  AND effective_start_date <= :asOfDate
  AND (effective_end_date IS NULL OR effective_end_date >= :asOfDate );

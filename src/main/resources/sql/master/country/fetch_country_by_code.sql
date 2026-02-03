SELECT country_id
FROM master.md_country
WHERE country_code = :countryCode
  AND is_active = 1
  AND :effectiveDate >= effective_start_date
  AND (effective_end_date IS NULL OR :effectiveDate <= effective_end_date);

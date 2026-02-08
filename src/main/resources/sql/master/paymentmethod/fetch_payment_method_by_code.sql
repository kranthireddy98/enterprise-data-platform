SELECT payment_method_id as id, method_code as code
FROM master.md_payment_method
WHERE method_code = :code;


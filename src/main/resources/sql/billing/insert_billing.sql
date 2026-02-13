INSERT INTO core.customer_billing_account(
                                  customer_id,
                                  currency_id,
                                  payment_method_id,
                                  is_active
)VALUES (
         :customerId,
         :currencyId,
         :paymentMethodId,
         1
        );
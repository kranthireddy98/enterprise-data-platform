package com.EnterprisePlatform.repository.support;

public enum SqlKey {



    CUSTOMER_FETCH_BY_ID("sql/customer/select_customer_by_id.sql"),
    CUSTOMER_FETCH_ALL("sql/customer/select_customers.sql"),
    CUSTOMER_INSERT("sql/customer/insert_customers.sql"),
    CUSTOMER_DELETE("sql/customer/select_customers.sql"),
    CUSTOMER_LAST_ID("sql/customer/customer_last_id.sql"),



    //ADDRESS
    ADDRESS_DELETE_BY_CUSTOMER("sql/address/delete_address_by_customerId.sql"),


    //country
    COUNTRY_FETCH_ACTIVE_BY_CODE("sql/master/country/fetch_country_by_code.sql"),

    //state
    MASTER_STATE_BY_CODE("sql/master/state/fetch_state_by_code.sql"),

    //product
    MASTER_PRODUCT_BY_CODE("sql/master/product/fetch_product_by_code.sql"),

    //currency
    MASTER_CURRENCY_BY_CODE("sql/master/currency/fetch_currency_by_code.sql"),

    //Payment Method
    MASTER_PAYMENT_METHOD_BY_CODE("sql/master/paymentmethod/fetch_payment_method_by_code.sql"),

    //Tax Code
    MASTER_TAX_CODE_BY_CODE("sql/master/taxcode/fetch_taxcode_by_code.sql"),

    //billing
    BILLING_INSERT("sql/billing/insert_billing.sql"),

    //Bulk Ops
    BULK_ERROR_INSERT("sql/ops/bulk_error_insert.sql"),
    BULK_JOB_CREATE("sql/ops/bulk_job_create.sql"),
    BULK_JOB_LAST_ID("sql/ops/bulk_job_last_id.sql"),
    BULK_JOB_COMPLETE("sql/ops/bulk_job_complete.sql"),

    BULK_ROW_CREATE("sql/ops/bulk_row_create.sql"),
    BULK_ROW_LAST_ID("sql/ops/bulk_row_last_id.sql"),
    BULK_ROW_MARK_SUCCESS("sql/ops/bulk_row_mark_success.sql"),
    BULK_ROW_MARK_FAILED("sql/ops/bulk_row_mark_failed.sql");




    private final String key;

    public String getKey() {
        return key;
    }

    SqlKey(String key){
        this.key =key;
    }

    public String key(){
        return  key;
    }
}

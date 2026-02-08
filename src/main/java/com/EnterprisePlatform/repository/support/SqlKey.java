package com.EnterprisePlatform.repository.support;

public enum SqlKey {



    CUSTOMER_FETCH_BY_ID("sql/customer/select_customer_by_id.sql"),
    CUSTOMER_FETCH_ALL("sql/customer/select_customers.sql"),
    CUSTOMER_INSERT("sql/customer/insert_customers.sql"),
    CUSTOMER_DELETE("sql/customer/select_customers.sql"),



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
    MASTER_TAX_CODE_BY_CODE("sql/master/taxcode/fetch_taxcode_by_code.sql");

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

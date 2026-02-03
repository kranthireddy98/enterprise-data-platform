package com.enterprise.platform.repository.support;

public enum SqlKey {



    CUSTOMER_FETCH_BY_ID("sql/customer/select_customer_by_id.sql"),
    CUSTOMER_FETCH_ALL("sql/customer/select_customers.sql"),
    CUSTOMER_INSERT("sql/customer/insert_customers.sql"),
    CUSTOMER_DELETE("sql/customer/select_customers.sql"),


    //country
    COUNTRY_FETCH_ACTIVE_BY_CODE("sql/master/country/fetch_country_by_code.sql");

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

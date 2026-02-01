package com.enterprise.platform.repository.support;

public enum SqlKey {



    CUSTOMER_FETCH_BY_ID("sql/customer/select_customer_by_id.sql"),
    CUSTOMER_FETCH_ALL("sql/customer/select_customers.sql");

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

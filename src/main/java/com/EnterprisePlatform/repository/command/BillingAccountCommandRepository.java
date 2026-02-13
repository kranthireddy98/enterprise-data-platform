package com.EnterprisePlatform.repository.command;

import com.EnterprisePlatform.DTO.ResolvedCustomerData;
import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BillingAccountCommandRepository {

    private final JdbcExecutor jdbc;
    private final SqlRegistry sqlRegistry;

    public BillingAccountCommandRepository(JdbcExecutor jdbc, SqlRegistry sqlRegistry) {
        this.jdbc = jdbc;
        this.sqlRegistry = sqlRegistry;
    }


    public void insert(Connection con, Long customerId, ResolvedCustomerData validated) {

        HashMap<String,Object> params = new HashMap<>();
        params.put("customerId",customerId);
        params.put("currencyId",validated.currencyId());
        params.put("paymentMethodId",validated.paymentMethodId());
        jdbc.update(
                con,
                sqlRegistry.get(SqlKey.BILLING_INSERT),
                params
        );
    }
}

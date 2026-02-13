package com.EnterprisePlatform.repository.command;

import com.EnterprisePlatform.DTO.ResolvedCustomerData;
import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerCommandRepository {

    private static final Logger log = LoggerFactory.getLogger(CustomerCommandRepository.class);
    private final SqlRegistry sqls;
    private final JdbcExecutor jdbc;


    public CustomerCommandRepository(SqlRegistry sqls, JdbcExecutor jdbc) {
        this.sqls = sqls;
        this.jdbc = jdbc;
    }

    public int insert(Connection con, Map<String,Object> params){
        return jdbc.update(con,
                sqls.get(SqlKey.CUSTOMER_INSERT),
               params );
    }

    public Long insert(Connection con, ResolvedCustomerData data) {

        log.info("Inserting for: {}",data.customerNumber());

        Map<String, Object> params = new HashMap<>();
        params.put("customerNumber", data.customerNumber());
        params.put("fullName", data.fullName());
        params.put("countryId", data.countryId());
        params.put("startDate", data.effectiveStartDate());
        params.put("endDate", data.effectiveEndDate());
         return jdbc.query(
                con,
                sqls.get(SqlKey.CUSTOMER_INSERT), params
        ).stream()
                 .map(r -> ((Number) r.get("customer_id")).longValue())
                 .findFirst().orElseThrow();

    }

    public int delete(Connection con, Long customerId){
        return jdbc.update(
                con,
                sqls.get(SqlKey.CUSTOMER_DELETE),
                Map.of("customerId",customerId)
        );
    }
}
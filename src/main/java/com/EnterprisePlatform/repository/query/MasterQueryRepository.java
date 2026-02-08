package com.EnterprisePlatform.repository.query;

import com.EnterprisePlatform.executor.JdbcExecutor;
import com.EnterprisePlatform.master.MasterType;
import com.EnterprisePlatform.repository.support.SqlKey;
import com.EnterprisePlatform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class MasterQueryRepository {

    private final JdbcExecutor jdbc;
    private final SqlRegistry sqlRegistry;

    public MasterQueryRepository(JdbcExecutor jdbc, SqlRegistry sqlRegistry) {
        this.jdbc = jdbc;
        this.sqlRegistry = sqlRegistry;
    }

    public List<Map<String,Object>> findActiveByCode(
            MasterType type,
            String code,
            LocalDate asOfDate
    ){
        SqlKey key = switch (type){
            case COUNTRY -> SqlKey.COUNTRY_FETCH_ACTIVE_BY_CODE;
            case STATE -> SqlKey.MASTER_STATE_BY_CODE;
            case PRODUCT -> SqlKey.MASTER_PRODUCT_BY_CODE;
            case CURRENCY -> SqlKey.MASTER_CURRENCY_BY_CODE;
            case PAYMENT_METHOD -> SqlKey.MASTER_PAYMENT_METHOD_BY_CODE;
            case TAX_CODE -> SqlKey.MASTER_TAX_CODE_BY_CODE;
        };

        return jdbc.query(sqlRegistry.get(key),
                Map.of(
                        "code", code,
                        "asOfDate", asOfDate)
                );
    }
}

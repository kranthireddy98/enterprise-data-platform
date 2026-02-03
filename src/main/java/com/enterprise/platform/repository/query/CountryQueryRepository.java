package com.enterprise.platform.repository.query;


import com.enterprise.platform.repository.support.JdbcExecutor;
import com.enterprise.platform.repository.support.SqlKey;
import com.enterprise.platform.repository.support.SqlRegistry;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class CountryQueryRepository {

    private final SqlRegistry sqls;
    private final JdbcExecutor jdbc;

    public CountryQueryRepository(SqlRegistry sqls, JdbcExecutor jdbc) {
        this.sqls = sqls;
        this.jdbc = jdbc;
    }

    public Long fetchActiveCountryId(
            String countryCode,
            LocalDate effectiveDate
    ) {
        List<Map<String, Object>> rows =
                jdbc.query(
                        sqls.get(SqlKey.COUNTRY_FETCH_ACTIVE_BY_CODE),
                        Map.of(
                                "countryCode", countryCode,
                                "effectiveDate", effectiveDate
                        )
                );

        if (rows.isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid or inactive country: " + countryCode
            );
        }

        return ((Number) rows.get(0).get("country_id")).longValue();
    }
}

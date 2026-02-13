package com.EnterprisePlatform.repository.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.core.log.LogFormatUtils.formatValue;

@Component
public class NamedParameterBinder {

    private static final Logger log = LoggerFactory.getLogger(NamedParameterBinder.class);

    public PreparedStatement bind(
            Connection con,
            String sql,
            Map<String,Object> params
    )throws SQLException{
        List<Object> values = new ArrayList<>();

        String parsedSql = parse(sql,params,values);
        String debugSql = buildDebugSql(parsedSql, values);
        log.info(debugSql);
        PreparedStatement ps = con.prepareStatement(parsedSql);

        for(int i = 0;i<values.size();i++){
            ps.setObject(i+1,values.get(i));
        }

        return ps;
    }

    private String parse(String sql, Map<String,Object> params, List<Object> values){
        Matcher m = Pattern.compile(":(\\w+)").matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()){
            String name = m.group(1);

            if(!params.containsKey(name)){
                throw new IllegalStateException("Missing param: " + name);
            }

            values.add(params.get(name));
            m.appendReplacement(sb,"?");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String buildDebugSql(String parsedSql, List<Object> orderedParams) {

        StringBuilder result = new StringBuilder();
        int paramIndex = 0;

        for (int i = 0; i < parsedSql.length(); i++) {
            char ch = parsedSql.charAt(i);

            if (ch == '?' && paramIndex < orderedParams.size()) {

                Object value = orderedParams.get(paramIndex++);

                result.append(formatValue(value));

            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    private String formatValue(Object value) {

        if (value == null) {
            return "NULL";
        }

        if (value instanceof String) {
            return "'" + value.toString().replace("'", "''") + "'";
        }

        if (value instanceof java.time.LocalDate
                || value instanceof java.time.LocalDateTime) {
            return "'" + value.toString() + "'";
        }

        return value.toString();
    }


}

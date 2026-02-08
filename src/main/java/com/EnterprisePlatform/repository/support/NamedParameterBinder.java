package com.EnterprisePlatform.repository.support;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NamedParameterBinder {

    public PreparedStatement bind(
            Connection con,
            String sql,
            Map<String,Object> params
    )throws SQLException{
        List<Object> values = new ArrayList<>();

        String parsedSql = parse(sql,params,values);

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

}

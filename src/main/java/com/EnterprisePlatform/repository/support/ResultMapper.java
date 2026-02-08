package com.EnterprisePlatform.repository.support;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResultMapper {

    public List<Map<String,Object>> map(ResultSet rs) throws SQLException {

        List<Map<String,Object>> rows = new ArrayList<>();

        ResultSetMetaData md = rs.getMetaData();

        int cols = md.getColumnCount();

        while (rs.next()){
            Map<String,Object> row = new LinkedHashMap<>();
            for (int i =1; i<= cols;i++){
                row.put(md.getColumnLabel(i),rs.getObject(i));
            }

            rows.add(row);
        }
        return rows;
    }
}

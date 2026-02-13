package com.EnterprisePlatform.executor;

import com.EnterprisePlatform.infrastructure.datasource.DataSourceManager;
import com.EnterprisePlatform.infrastructure.datasource.DataSourceType;
import com.EnterprisePlatform.infrastructure.datasource.HikariConfigFactory;
import com.EnterprisePlatform.repository.support.NamedParameterBinder;
import com.EnterprisePlatform.repository.support.ResultMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Component
public class JdbcExecutor {

    private static  final Logger log = LoggerFactory.getLogger(JdbcExecutor.class);
    private final DataSourceManager dataSourceManager;
    private final HikariConfigFactory configFactory;
    private final NamedParameterBinder binder;
    private  final ResultMapper mapper;

    public JdbcExecutor(DataSourceManager dataSourceManager, NamedParameterBinder binder,
                        ResultMapper mapper,HikariConfigFactory configFactory) {
        this.dataSourceManager = dataSourceManager;
        this.binder = binder;
        this.mapper = mapper;
        this.configFactory = configFactory;
    }

    public DataSource getDatasource(){
        return dataSourceManager.getOrCreate(DataSourceType.APPLICATION,configFactory.create(DataSourceType.APPLICATION));
    }

    public List<Map<String,Object>> query(
            String sql,
            Map<String,Object> params
    ){

        try (Connection con = getDatasource().getConnection();
             PreparedStatement ps = binder.bind(con,sql,params);
             ResultSet rs = ps.executeQuery()){
            return  mapper.map(rs);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public List<Map<String,Object>> query(
            Connection con,
            String sql,
            Map<String,Object> params
    ) throws Exception {
        try (PreparedStatement ps = binder.bind(con,sql,params);
             ResultSet rs = ps.executeQuery()){
            return  mapper.map(rs);
        }catch (Exception e){
            log.error("Error: {}",e);
            throw e;
        }
    }

    public int update(
            String sql,
            Map<String,Object> params
    ){
        try(Connection con = getDatasource().getConnection();
        PreparedStatement ps = binder.bind(con,sql,params)){
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(
            Connection con,
            String sql,
            Map<String,Object> params
    )  {
        try (PreparedStatement ps  = binder.bind(con,sql,params)){
            return ps.executeUpdate();

        }catch (Exception e){
            throw new RuntimeException("updated Failed",e);
        }
    }
}

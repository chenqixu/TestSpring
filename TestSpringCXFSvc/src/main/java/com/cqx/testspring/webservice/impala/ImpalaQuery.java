package com.cqx.testspring.webservice.impala;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ImpalaQuery
 *
 * @author chenqixu
 */
@Component
public class ImpalaQuery {

    @Resource(name = "namedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate npjt;

    public void query(String table_name, int limit) {
        String sql = "select * from " + table_name + " limit " + limit;
        Map<String, String> paramMap = new HashMap<>();
        List<Map<String, Object>> result = npjt.queryForList(sql, paramMap);
        for (Map<String, Object> r : result) {
            System.out.println(r);
        }
    }

}

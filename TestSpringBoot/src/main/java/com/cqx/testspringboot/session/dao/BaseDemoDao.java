package com.cqx.testspringboot.session.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * BaseDemoDao
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspringboot.session.dao.BaseDemoDao")
public class BaseDemoDao extends DemoDao {

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
}

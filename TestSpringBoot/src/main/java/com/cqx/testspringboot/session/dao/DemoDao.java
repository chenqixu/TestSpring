package com.cqx.testspringboot.session.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * DemoDao
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspringboot.session.dao.DemoDao")
public class DemoDao {

    private static final Logger logger = LoggerFactory.getLogger(DemoDao.class);

    @Resource(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate;
    @Resource(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void test() {
        logger.info("this：{}，this.jdbcTemplate：{}，this.namedParameterJdbcTemplate：{}",
                this, this.jdbcTemplate, this.namedParameterJdbcTemplate);
    }

//    public JdbcTemplate getJdbcTemplate() {
//        return jdbcTemplate;
//    }
//
//    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
//        return namedParameterJdbcTemplate;
//    }
}

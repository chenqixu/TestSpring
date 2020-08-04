package com.cqx.testspringboot.common.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * MyDBConfiguration 多数据源适配<br>
 * &nbsp;&nbsp;Primary表示默认<br>
 * &nbsp;&nbsp;@Bean(name = "xxx")进行声明，用于@Resource(name = "xxxx")<br>
 * &nbsp;&nbsp;或者使用@Qualifier注解
 *
 * @author chenqixu
 */
@Configuration
public class MyDBConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ywxxDatasource")
    @ConfigurationProperties(prefix = "spring.datasource-ywxx")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplate")
    @Primary
    public JdbcTemplate primaryJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "namedParameterJdbcTemplate")
    @Primary
    public NamedParameterJdbcTemplate primaryNamedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "ywxxJdbcTemplate")
    public JdbcTemplate ywxxJdbcTemplate(@Qualifier("ywxxDatasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "ywxxNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate ywxxNamedParameterJdbcTemplate(@Qualifier("ywxxDatasource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}

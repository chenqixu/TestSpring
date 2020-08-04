package com.cqx.testspringboot.kafka.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * SchemaDao
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspringboot.kafka.dao.SchemaDao")
public class SchemaDao {
    private static final Logger logger = LoggerFactory.getLogger(SchemaDao.class);
    @Resource(name = "ywxxJdbcTemplate")
    public JdbcTemplate ywxxJdbcTemplate;

    public String querySchemaByTopicName(String topic) {
        final LobHandler lobHandler = new DefaultLobHandler();
        String sql = "select avsc from nmc_schema where schema_name in(select schema_name from nmc_topic where topic_name='" + topic + "')";
        List<String> results = ywxxJdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int i) throws SQLException {
                String avsc = lobHandler.getClobAsString(rs, "AVSC");
                logger.info("avsc：{}", avsc);
                return avsc;
            }
        });
        if (results != null && results.size() > 0) return results.get(0);
        return "";
    }
}

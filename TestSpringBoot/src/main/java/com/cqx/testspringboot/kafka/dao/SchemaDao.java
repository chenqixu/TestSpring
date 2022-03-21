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
import java.util.ArrayList;
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

    public List<String> queryTopicList() {
        String sql = "select topic_name from nmc_topic";
        List<String> results = ywxxJdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getString("topic_name");
            }
        });
        return results;
    }

    /**
     * CTG：Cluster、Topic、GroupID<br>
     * 通过集群名称、话题名称、消费组获取对应的schema<br>
     * <pre>
     *     集群是默认值：kafka、kafka-log
     *     消费组是默认值：default
     * </pre>
     *
     * @param cluster  单个集群
     * @param topic    话题
     * @param group_id 消费组
     * @return
     */
    public String querySchemaByCTG(String cluster, String topic, String group_id) {
        List<String> clusters = new ArrayList<>();
        if (cluster == null || cluster.length() == 0) {
            clusters.add("kafka");
            clusters.add("kafka-log");
        } else {
            clusters.add(cluster);
        }
        if (group_id == null || group_id.length() == 0) {
            group_id = "default";
        }
        return querySchemaByCTG(clusters, topic, group_id);
    }

    /**
     * CTG：Cluster、Topic、GroupID<br>
     * 通过集群名称、话题名称、消费组获取对应的schema
     *
     * @param clusters 多个集群
     * @param topic    话题
     * @param group_id 消费组
     * @return
     */
    public String querySchemaByCTG(List<String> clusters, String topic, String group_id) {
        StringBuilder clusterSB = new StringBuilder();
        for (int i = 0; i < clusters.size(); i++) {
            clusterSB.append("'");
            clusterSB.append(clusters.get(i).toUpperCase());
            clusterSB.append("'");
            if (i < clusters.size() - 1) {
                clusterSB.append(",");
            }
        }
        String sql = String.format("select avsc from nmc_schema where schema_name in(" +
                        "select schema_name from nmc_topic where upper(topic_name)='%s' " +
                        "and upper(cluster_name) in (%s) " +
                        "and upper(group_id)='%s')"
                , topic.toUpperCase(), clusterSB.toString(), group_id.toUpperCase());
        logger.info("querySchemaByCTG：{}", sql);
        return getClobAsString(sql, clusterSB.toString(), topic, group_id);
    }

    /**
     * 获取clob，转成字符串
     *
     * @param sql      查询语句
     * @param cluster  集群
     * @param topic    话题
     * @param group_id 消费组
     * @return
     */
    private String getClobAsString(String sql, String cluster, String topic, String group_id) {
        final LobHandler lobHandler = new DefaultLobHandler();
        List<String> results = ywxxJdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int i) throws SQLException {
                String avsc = lobHandler.getClobAsString(rs, "AVSC");
                logger.debug("cluster：{}，topic：{}，group_id：{}，avsc：{}", cluster, topic, group_id, avsc);
                return avsc;
            }
        });
        if (results != null && results.size() > 0) return results.get(0);
        return "";
    }
}

package com.cqx.testspringboot.kafka;

import com.cqx.testspringboot.kafka.dao.SchemaDao;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * SchemaService
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("SchemaService")
public class SchemaService {

    @Resource
    private SchemaDao schemaDao;

    /**
     * CTG：Cluster、Topic、GroupID<br>
     * 通过集群名称、话题名称、消费组获取对应的schema<br>
     * <pre>
     *     集群是默认值：kafka、kafka-log
     *     消费组是默认值：default
     * </pre>
     *
     * @param cluster  集群
     * @param topic    话题
     * @param group_id 消费组
     * @return avro的schema
     */
    @RequestMapping(value = "/getSchema", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSchema(@RequestParam(value = "c", required = false) String cluster
            , @RequestParam(value = "t") String topic
            , @RequestParam(value = "g", required = false) String group_id) {
        return schemaDao.querySchemaByCTG(cluster, topic, group_id);
    }

    @RequestMapping(value = "/getTopicList", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTopicList() {
        return schemaDao.queryTopicList();
    }
}

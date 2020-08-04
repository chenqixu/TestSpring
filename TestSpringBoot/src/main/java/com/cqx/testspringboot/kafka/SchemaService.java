package com.cqx.testspringboot.kafka;

import com.cqx.testspringboot.kafka.dao.SchemaDao;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @RequestMapping(value = "/getSchema", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryTenantsByTenantLevel(@RequestParam(value = "t") String topic) {
        return schemaDao.querySchemaByTopicName(topic);
    }
}

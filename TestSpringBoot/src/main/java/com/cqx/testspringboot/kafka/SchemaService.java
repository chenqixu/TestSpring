package com.cqx.testspringboot.kafka;

import com.cqx.testspringboot.kafka.bean.SchemaBean;
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

    @RequestMapping(value = "/getSchema", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSchema(@RequestParam(value = "t") String topic) {
        return schemaDao.querySchemaByTopicName(topic);
    }

    @RequestMapping(value = "/updateSchema", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateSchema(@RequestBody SchemaBean schemaBean) {
        schemaDao.updateSchemaByTopicNameAndValue(schemaBean.getTopic(), schemaBean.getSchemaStr().toJSONString());
    }

    @RequestMapping(value = "/getTopicList", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTopicList() {
        return schemaDao.queryTopicList();
    }
}

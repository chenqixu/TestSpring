package com.cqx.testspringboot.kafka;

import com.cqx.testspringboot.kafka.bean.Alarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DataService
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("DataService")
public class DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    @RequestMapping(value = "/load", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String setUserInfo(@RequestBody List<Alarm> alarms) {
        logger.info("receive size：{}", alarms.size());
        for (Alarm alarm : alarms)
            logger.info("receive：{}", alarm);
        return "success";
    }
}

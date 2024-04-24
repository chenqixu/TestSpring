package com.cqx.testspringboot.configmanager;

import com.cqx.testspringboot.activiti.dao.CommonBaseDao;
import com.cqx.testspringboot.configmanager.model.SvcConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公共配置管理服务
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("${cmcc.web.servlet-path}/config")
public class ConfigManagerServer {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManagerServer.class);

    @Resource(name = "com.cqx.testspringboot.activiti.dao.CommonBaseDao")
    protected CommonBaseDao dao;

    @RequestMapping(value = "/queryUrlConfigByName", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryUrlConfigByName(@RequestBody String config_name) {
        logger.info("[queryConfigByName] config_name={}", config_name);
        List<SvcConfigBean> configList = dao.query("select config_name,param_name,param_value " +
                        " from svc_config where config_name='" + config_name + "' and param_name='url'"
                , SvcConfigBean.class);
        if (configList != null && configList.size() > 0) {
            return configList.get(0).getParam_value();
        } else {
            return null;
        }
    }
}

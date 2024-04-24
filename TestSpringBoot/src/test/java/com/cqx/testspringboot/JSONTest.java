package com.cqx.testspringboot;

import com.alibaba.fastjson.JSON;
import com.cqx.testspringboot.scheduling.model.SvcRunningTaskBean;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON测试
 *
 * @author chenqixu
 */
public class JSONTest {

    @Test
    public void json1() {
        Map<String, String> param = new HashMap<>();
        param.put("v1", "123");
        System.out.println(JSON.toJSONString(param));

        SvcRunningTaskBean svcRunningTaskBean = new SvcRunningTaskBean();
        svcRunningTaskBean.setTask_id("1");
        svcRunningTaskBean.setTask_desc("测试");
        svcRunningTaskBean.setTask_class("com.newland.bd.nodemanager.impl.TestTask");
        svcRunningTaskBean.setTask_commands("{\"v1\":\"123\"}");
        System.out.println(JSON.toJSONString(svcRunningTaskBean));

        System.out.println(System.currentTimeMillis());
    }
}

package com.cqx.testspring.webservice.operhis.model.javabean;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class OperhisBeanTest {

    @Test
    public void get() {
        OperhisBean operhisBean = new OperhisBean();
        operhisBean.setUser_id("10001");
        operhisBean.setOper_desc("login");
        operhisBean.setOper_time("2020-02-29");
        System.out.println(JSON.toJSONString(operhisBean));

        System.out.println(OperhisResultBean.class.getName());
    }
}
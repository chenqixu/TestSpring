package com.cqx.testspring.webservice.operhis.service;

import com.alibaba.fastjson.JSON;
import com.cqx.testspring.webservice.operhis.model.OperhisReqObject;
import com.cqx.testspring.webservice.operhis.model.OperhisRespObject;
import com.cqx.testspring.webservice.operhis.model.javabean.LoginBean;
import org.junit.Before;
import org.junit.Test;

public class BaseServiceMgtTest {

    @Before
    public void setUp() throws Exception {
        BaseServiceMgt.register(LoginServiceImpl.class);
    }

    @Test
    public void exec() {
        LoginBean loginBean = new LoginBean();
        loginBean.setName("10001");
        loginBean.setPasswd("123456");
        OperhisReqObject operhisReqObject = new OperhisReqObject();
        operhisReqObject.setMgt_name("LoginServiceImpl");
        operhisReqObject.setFunc_name("login");
        operhisReqObject.setReq_class(LoginBean.class.getName());
        operhisReqObject.setReq_content(JSON.toJSONString(loginBean));
        OperhisRespObject operhisRespObject = BaseServiceMgt.exec(operhisReqObject);
        System.out.println("[Resq_class]" + operhisRespObject.getResq_class());
        System.out.println("[Resp_conent]" + operhisRespObject.getResp_conent());
    }
}
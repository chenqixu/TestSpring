package com.cqx.testspring.webservice.operhis.service;

import com.alibaba.fastjson.JSON;
import com.cqx.testspring.webservice.operhis.model.OperhisReqObject;
import com.cqx.testspring.webservice.operhis.model.OperhisRespObject;
import com.cqx.testspring.webservice.operhis.model.javabean.LoginBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import javax.annotation.Resource;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:resources/spring/spring-*.xml"})
public class BaseServiceMgtTest {

//    @Resource
//    private LoginServiceDaoImpl loginServiceDao;
//
//    @Resource
//    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        BaseServiceMgt.register(LoginServiceImpl.class);
    }

    @Test
    public void exec() {
//        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:resources/spring/spring-*.xml");
//        // 模拟HttpServlet请求
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setContextPath("classpath:resources/spring/spring-edc-mvc.xml");

//        ContextLoader contextLoader = new ContextLoader();
//        contextLoader.initWebApplicationContext(request.getServletContext());
//        System.out.println(ContextLoader.getCurrentWebApplicationContext());
//        System.exit(-1);
//        System.out.println(wac);
//        loginServiceDao.queryLogin();
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
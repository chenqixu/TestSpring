package com.cqx.testspring.webservice.common.manage;

import com.alibaba.fastjson.JSON;
import com.cqx.testspring.webservice.common.model.CommonReqObject;
import com.cqx.testspring.webservice.common.model.CommonRespObject;
import com.cqx.testspring.webservice.login.LoginBean;
import com.cqx.testspring.webservice.login.service.LoginServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:resources/spring/spring-*.xml"})
public class CommonMgtImplTest {

    @Autowired
    private ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        CommonMgtImpl.register(LoginServiceImpl.class);
        CommonMgtImpl.initWac(context);
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
        CommonReqObject commonReqObject = new CommonReqObject();
        commonReqObject.setMgt_name("LoginServiceImpl");
        commonReqObject.setFunc_name("login");
        commonReqObject.setReq_class(LoginBean.class.getName());
        commonReqObject.setReq_content(JSON.toJSONString(loginBean));
        CommonRespObject commonRespObject = CommonMgtImpl.exec(commonReqObject);
        System.out.println("[Resq_class]" + commonRespObject.getResq_class());
        System.out.println("[Resp_conent]" + commonRespObject.getResp_conent());
    }
}
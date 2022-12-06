package com.cqx.testweb.servlet;

import com.cqx.testweb.bean.VideoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletException;

// 使用这个WebAppConfiguration会在跑单元测试的时候真实的启一个web服务
@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath*:resources/spring/spring-*.xml"})
@ContextConfiguration("classpath*:resources/spring/spring-context.xml")
public class VideoServletTest extends AbstractTestNGSpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(VideoServletTest.class);
    // 注入web环境的ApplicationContext容器
    @Autowired
    private WebApplicationContext wac;
    private MockServletConfig mockServletConfig;

    @BeforeMethod
    public void setUp() {
        MockServletContext mockServletContext = new MockServletContext();
        mockServletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
        mockServletConfig = new MockServletConfig(mockServletContext);
    }

    @Test
    public void testPrepare() throws ServletException {
        VideoServlet vs = new VideoServlet("Z:\\Reader\\web\\");
        vs.init(mockServletConfig);
        VideoBean vb = vs.getVideoBean("normal", "1");
        logger.info("getContent: {}, getAllName: {}", vb.getContent(), vb.getAllName());
    }
}

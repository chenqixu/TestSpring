package com.cqx.testweb.action;

import com.alibaba.fastjson.JSON;
import com.cqx.common.utils.list.ListHelper;
import com.cqx.testweb.bean.ComicPageRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// 使用这个WebAppConfiguration会在跑单元测试的时候真实的启一个web服务
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:resources/spring/spring-*.xml"})
public class ComicControllerTest extends AbstractTransactionalTestNGSpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(ComicControllerTest.class);
    // 注入web环境的ApplicationContext容器
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeMethod
    public void setUp() {
        // 创建一个MockMvc进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testQueryBeanListComic_month() throws Exception {
        ComicPageRequestBean requestBean = new ComicPageRequestBean();
        requestBean.setPage(1);
        requestBean.setPageNum(15);
        requestBean.setTableName("comic_month");
        requestBean.setColumns(ListHelper.getInstance(String.class).add("month_name").get());
        // perform用于执行一个请求
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/comic/page.do")
                        // 用contentType表示具体请求中的媒体类型信息
                        .contentType(MediaType.APPLICATION_JSON)
                        // 请求内容
                        .content(JSON.toJSONString(requestBean))
                        //accept指定客户端能够接收的内容类型
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())// 增加一个结果处理器
                .andExpect(MockMvcResultMatchers.status().isOk())// 执行完成后的断言
                .andReturn();// 执行完成后返回相应的结果
        String content = result.getResponse().getContentAsString();
        logger.info("content：{}", content);
//        JSONObject jsonObject = JSON.parseObject(content);
        //采用Asser的方式进行断言
//        Assert.assertEquals(jsonObject.get("code"), "200");
    }

    @Test
    public void testQueryBeanListComic_book() throws Exception {
        ComicPageRequestBean requestBean = new ComicPageRequestBean();
        requestBean.setPage(1);
        requestBean.setPageNum(15);
        requestBean.setTableName("comic_book");
        requestBean.setColumns(ListHelper.getInstance(String.class).add("month_name").add("book_name").add("book_desc").get());
        // perform用于执行一个请求
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/comic/page.do")
                        // 用contentType表示具体请求中的媒体类型信息
                        .contentType(MediaType.APPLICATION_JSON)
                        // 请求内容
                        .content(JSON.toJSONString(requestBean))
                        //accept指定客户端能够接收的内容类型
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())// 增加一个结果处理器
                .andExpect(MockMvcResultMatchers.status().isOk())// 执行完成后的断言
                .andReturn();// 执行完成后返回相应的结果
        String content = result.getResponse().getContentAsString();
        logger.info("content：{}", content);
    }
}
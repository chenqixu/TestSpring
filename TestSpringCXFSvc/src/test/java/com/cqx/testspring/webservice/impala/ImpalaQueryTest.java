package com.cqx.testspring.webservice.impala;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:resources/spring/spring-*.xml"})
public class ImpalaQueryTest {

    @Resource
    private ImpalaQuery impalaQuery;

    @Test
    public void query() {
        impalaQuery.query("hb_userlog_query", 5);
    }
}
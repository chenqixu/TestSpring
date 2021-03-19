package com.cqx.testspringboot.session.dao;

import com.cqx.testspringboot.TestspringbootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest(classes = TestspringbootApplication.class)
public class DemoDaoTest extends AbstractTestNGSpringContextTests {

    @Qualifier("com.cqx.testspringboot.session.dao.DemoDao")
    @Autowired
    private DemoDao demoDao;

    @Test
    public void testTest1() {
        demoDao.test();
    }
}
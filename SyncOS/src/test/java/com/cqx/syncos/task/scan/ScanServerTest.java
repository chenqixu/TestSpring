package com.cqx.syncos.task.scan;

import com.cqx.syncos.task.cache.CacheServer;
import com.cqx.syncos.util.SleepUtil;
import com.cqx.syncos.util.file.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScanServerTest {

    private static final Logger logger = LoggerFactory.getLogger(ScanServerTest.class);

    @Value("${syncos.task.data.path}")
    private String data_path;

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private ScanServer scanServer;
    private CacheServer cacheServer;

    @Before
    public void setUp() throws Exception {
        //扫描目录、读取配置
        for (String table_path : FileUtil.listFile(data_path)) {
            cacheServer = new CacheServer(data_path, table_path);
            scanServer = new ScanServer(cacheServer);
            scanServer.init(jdbcTemplate);
            break;
        }
    }

    @After
    public void tearDown() throws Exception {
        if (scanServer != null) scanServer.close();
    }

    @Test
    public void run() {
        if (scanServer != null) scanServer.start();
        SleepUtil.sleepMilliSecond(5000);
    }
}
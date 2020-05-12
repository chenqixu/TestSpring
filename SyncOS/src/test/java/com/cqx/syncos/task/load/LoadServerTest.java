package com.cqx.syncos.task.load;

import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.task.cache.CacheServer;
import com.cqx.syncos.util.SleepUtil;
import com.cqx.syncos.util.file.FileUtil;
import com.cqx.syncos.util.file.RAFFileMangerCenter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoadServerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoadServerTest.class);

    @Resource
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Value("${syncos.task.data.path}")
    private String data_path;

    private LoadServer loadServer;
    private CacheServer cacheServer;
    private RAFFileMangerCenter rafFileMangerCenter;

    @Before
    public void setUp() throws Exception {
        //扫描目录、读取配置
        for (String table_path : FileUtil.listFile(data_path)) {
            cacheServer = new CacheServer(data_path, table_path);
            loadServer = new LoadServer(cacheServer);
            loadServer.init(kafkaTemplate);
            break;
        }
        rafFileMangerCenter = new RAFFileMangerCenter(cacheServer);
    }

    @After
    public void tearDown() throws Exception {
        if (loadServer != null) loadServer.close();
    }

    @Test
    public void run() {
        logger.info("{}", data_path);
        loadServer.start();
        SleepUtil.sleepMilliSecond(5000);
    }

    @Test
    public void toKafkaValue() throws Exception {
        TaskInfo taskInfo = cacheServer.getTaskInfo();
        String[] src_fields_array = taskInfo.getSrc_fields().split(",", -1);
        String values = rafFileMangerCenter.read();
        String[] value_array = values.split(taskInfo.getScan_split(), -1);
        logger.info("split：{}", taskInfo.getScan_split().replace("\\", ""));
        logger.info("src_fields_array：{}", Arrays.asList(src_fields_array));
        logger.info("read：{}", values);
        logger.info("change：{}", Arrays.asList(value_array));
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < src_fields_array.length; i++) {
            map.put(src_fields_array[i], value_array[i]);
        }
        cacheServer.getGenericRecordUtil().genericRecord(map);
    }
}
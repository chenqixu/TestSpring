package com.cqx.syncos.task.load;

import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.task.cache.CacheServer;
import com.cqx.syncos.util.SleepUtil;
import com.cqx.syncos.util.file.RAFFileMangerCenter;
import com.cqx.syncos.util.kafka.GenericRecordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * LoadServer
 * <pre>
 *     1、扫描日志文件
 *     2、实时加载入kafka
 * </pre>
 *
 * @author chenqixu
 */
public class LoadServer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(LoadServer.class);
    private volatile boolean flag = true;
    private TaskInfo taskInfo;
    private CacheServer cacheServer;
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private RAFFileMangerCenter rafFileMangerCenter;
    private GenericRecordUtil genericRecordUtil;
    private String[] src_fields_array;

    public LoadServer(CacheServer cacheServer) {
        this(cacheServer.getTaskInfo());
        this.cacheServer = cacheServer;
        this.genericRecordUtil = cacheServer.getGenericRecordUtil();
        src_fields_array = taskInfo.getSrc_fields().split(",", -1);
    }

    private LoadServer(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        try {
            rafFileMangerCenter = new RAFFileMangerCenter(cacheServer);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        while (flag) {
            //扫描获取文件
            //根据缓存从第n行开始读取文件
            //按照规则入kafka
            //文件末尾写一个END表示文件结束
            try {
                String content = rafFileMangerCenter.read();
                if (content == null) {
                    SleepUtil.sleepMilliSecond(200);
                } else if (content.equals(RAFFileMangerCenter.END_TAG)) {
                    //文件切换操作
                    logger.info("文件切换操作……");
                    break;
                } else {
                    logger.info("read content：{}", content);
                    kafkaTemplate.send(taskInfo.getDst_name(), toKafkaValue(content));
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        //资源释放
        try {
            rafFileMangerCenter.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("Kafka加载任务停止：{}", taskInfo.getTask_name());
    }

    private byte[] toKafkaValue(String values) {
        Map<String, String> map = new HashMap<>();
        String[] value_array = values.split(taskInfo.getScan_split(), -1);
        for (int i = 0; i < src_fields_array.length; i++) {
            map.put(src_fields_array[i], value_array[i]);
        }
        return genericRecordUtil.genericRecord(map);
    }

    public boolean isThis(String task_name) {
        return taskInfo.getTask_name().equals(task_name);
    }

    public void close() {
        flag = false;
    }
}

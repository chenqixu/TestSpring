package com.cqx.syncos.task.load;

import com.cqx.syncos.task.bean.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

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
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    public LoadServer(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        //先读取缓存
        //没有缓存则扫描文件，然后创建一个缓存
    }

    private String getLoadFile() {
        //获取当前要加载的文件

        return "";
    }

    @Override
    public void run() {
        while (flag) {
            //扫描获取文件
            //根据缓存从第n行开始读取文件
            //按照规则入kafka

        }
        logger.info("Kafka加载任务停止：{}", taskInfo.getTask_name());
    }

    public void close() {
        flag = false;
    }
}

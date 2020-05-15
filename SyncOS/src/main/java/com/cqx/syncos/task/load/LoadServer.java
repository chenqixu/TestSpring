package com.cqx.syncos.task.load;

import com.cqx.syncos.task.bean.LoadBean;
import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.task.cache.CacheServer;
import com.cqx.syncos.util.SleepUtil;
import com.cqx.syncos.util.TimeCostUtil;
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
public class LoadServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(LoadServer.class);
    private volatile boolean flag = true;
    private TaskInfo taskInfo;
    private CacheServer cacheServer;
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private RAFFileMangerCenter rafFileMangerCenter;
    private GenericRecordUtil genericRecordUtil;
    private String[] src_fields_array;
    private TimeCostUtil runCost;
    private Long loadSeq = 0L;

    public LoadServer(CacheServer cacheServer) {
        this(cacheServer.getTaskInfo());
        this.cacheServer = cacheServer;
        this.genericRecordUtil = cacheServer.getGenericRecordUtil();
    }

    private LoadServer(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        this.src_fields_array = taskInfo.getSrc_fields().split(",", -1);
        this.runCost = new TimeCostUtil();
    }

    private String logHeader() {
        return String.format("加载【%s】", taskInfo.getTask_name());
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 资源初始化
     */
    private void resourceInit() {
        try {
            rafFileMangerCenter = new RAFFileMangerCenter(cacheServer);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        LoadBean loadBean = cacheServer.getLoadBean();
        if (loadBean != null && rafFileMangerCenter != null) {
            logger.info("{}文件 {} 从 {} 开始继续加载", logHeader(), loadBean.getLoad_file_name(), loadBean.getHeader_pos_next());
            rafFileMangerCenter.setHeader_pos(loadBean.getHeader_pos_next());
        }
    }

    @Override
    public void run() {
        logger.info("{}任务启动", logHeader());
        int tag = 0;
        //资源初始化
        resourceInit();
        while (flag) {
            //扫描获取文件
            //根据缓存从第n行开始读取文件
            //按照规则入kafka
            //文件末尾写一个END表示文件结束
            try {
                String content = rafFileMangerCenter.read();
                if (content == null) {
                    if (tag > 0) {
                        int loadCnt = tag;
                        tag = 0;
                        runCost.end();
                        //更新下一个加载位置到缓存中，强制更新
                        cacheServer.updateLoadCache(rafFileMangerCenter.getHeader_pos_next(), true);
                        logger.info("{}加载序号：{}，加载结束，加载耗时：{}，加载记录数：{}", logHeader(), getLoadSeq(), runCost.getCost(), loadCnt);
                    }
                    SleepUtil.sleepMilliSecond(200);
                } else if (content.equals(RAFFileMangerCenter.END_TAG)) {
                    //文件切换操作
                    logger.info("{}文件切换操作……", logHeader());
                    break;
                } else {
                    if (tag == 0) {
                        runCost.start();
                        logger.info("{}加载序号：{}，开始加载", logHeader(), increaseAndGetLoadSeq());
                    }
                    tag++;
                    logger.debug("{}read content：{}", logHeader(), content);
                    kafkaTemplate.send(taskInfo.getDst_name(), toKafkaValue(content));
                    //更新下一个加载位置到缓存中，缓存大概5秒更新一次
                    cacheServer.updateLoadCache(rafFileMangerCenter.getHeader_pos_next());
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        //资源释放
        release();
        logger.info("{}Kafka加载任务停止", logHeader());
    }

    /**
     * 资源释放
     */
    private void release() {
        try {
            rafFileMangerCenter.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private byte[] toKafkaValue(String values) {
        Map<String, String> map = new HashMap<>();
        String[] value_array = values.split(taskInfo.getScan_split(), -1);
        for (int i = 0; i < src_fields_array.length; i++) {
            map.put(src_fields_array[i], value_array[i]);
        }
        return genericRecordUtil.genericRecord(map);
    }

    private long increaseAndGetLoadSeq() {
        loadSeq++;
        return getLoadSeq();
    }

    private long getLoadSeq() {
        return loadSeq;
    }

    public void resetFlag() {
        flag = true;
    }

    public void close() {
        flag = false;
    }

    public boolean isClose() {
        return !flag;
    }
}

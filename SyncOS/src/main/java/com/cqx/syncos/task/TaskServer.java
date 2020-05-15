package com.cqx.syncos.task;

import com.alibaba.fastjson.JSON;
import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.task.bean.TaskStatus;
import com.cqx.syncos.task.cache.CacheServer;
import com.cqx.syncos.task.load.LoadServer;
import com.cqx.syncos.task.scan.ScanServer;
import com.cqx.syncos.util.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动扫描任务、加载任务
 *
 * @author chenqixu
 */
@Component
public class TaskServer {

    private static final Logger logger = LoggerFactory.getLogger(TaskServer.class);
    private static final String CACHE = ".cache";
    private static final String SCAN_TAG = "scan";
    private static final String LOAD_TAG = "load";

    @Value("${syncos.task.data.path}")
    private String data_path;

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Resource(name = "namedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private Map<String, CacheServer> cacheServerList;
    private Map<String, TaskInfo> taskInfoList;
    private Map<String, ScanServer> scanServerList;
    private Map<String, LoadServer> loadServerList;
    private Map<String, Thread> threadMap;

    public void init() {
        cacheServerList = new HashMap<>();
        taskInfoList = new HashMap<>();
        scanServerList = new HashMap<>();
        loadServerList = new HashMap<>();
        threadMap = new HashMap<>();
        //扫描目录、读取配置
        for (String table_name : FileUtil.listFile(data_path)) {
            addTask(table_name);
        }
    }

    public void addTask(String task_name) {
        CacheServer cacheServer = new CacheServer(data_path, task_name);
        //加入缓存服务列表
        cacheServerList.put(task_name, cacheServer);
        //加入任务列表
        taskInfoList.put(task_name, cacheServer.getTaskInfo());
        //生成扫描服务并启动
        ScanServer scanServer = new ScanServer(cacheServer);
        scanServer.init(jdbcTemplate);
        startTask(SCAN_TAG, task_name, scanServer);
        scanServerList.put(task_name, scanServer);
        //生成加载服务并启动
        LoadServer loadServer = new LoadServer(cacheServer);
        loadServer.init(kafkaTemplate);
        startTask(LOAD_TAG, task_name, loadServer);
        loadServerList.put(task_name, loadServer);
    }

    private void monitorTask() {

    }

    private void startTask(String tag, String task_name, Runnable runnable) {
        Thread thread = new Thread(runnable);
        threadMap.put(tag + task_name, thread);
        thread.start();
    }

    public void startTask(String task_name) {
        ScanServer scanServer = scanServerList.get(task_name);
        LoadServer loadServer = loadServerList.get(task_name);
        if (scanServer != null && scanServer.isClose() && loadServer != null && loadServer.isClose()) {
            scanServer.resetFlag();
            startTask(SCAN_TAG, task_name, scanServer);
            loadServer.resetFlag();
            startTask(LOAD_TAG, task_name, loadServer);
        }
    }

    public void stopTask(String task_name) {
        ScanServer scanServer = scanServerList.get(task_name);
        LoadServer loadServer = loadServerList.get(task_name);
        if (scanServer != null && loadServer != null) {
            scanServer.close();
            loadServer.close();
            stopThreadAndJoin(SCAN_TAG, task_name);
            stopThreadAndJoin(LOAD_TAG, task_name);
        }
    }

    private void stopThreadAndJoin(String tag, String task_name) {
        Thread thread = threadMap.get(tag + task_name);
        if (thread != null) {
            try {
                thread.join();
                threadMap.remove(thread);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public TaskStatus statusTask(String task_name) {
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTask_name(task_name);
        ScanServer scanServer = scanServerList.get(task_name);
        LoadServer loadServer = loadServerList.get(task_name);
        if (scanServer != null && loadServer != null) {
            taskStatus.setScan_isRun(!scanServer.isClose());
            taskStatus.setLoad_isRun(!loadServer.isClose());
            return taskStatus;
        }
        return null;
    }

    public List<TaskStatus> statusAllTask() {
        List<TaskStatus> taskStatusList = new ArrayList<>();
        for (Map.Entry<String, TaskInfo> entry : taskInfoList.entrySet()) {
            TaskInfo taskInfo = entry.getValue();
            TaskStatus taskStatus = statusTask(taskInfo.getTask_name());
            if (taskStatus != null) taskStatusList.add(taskStatus);
        }
        return taskStatusList;
    }

    public void stopAll() {
        for (Map.Entry<String, TaskInfo> entry : taskInfoList.entrySet()) {
            TaskInfo taskInfo = entry.getValue();
            stopTask(taskInfo.getTask_name());
        }
    }

    /**
     * 用于测试kafka发送
     *
     * @param topic
     */
    public void sendKafka(String topic) {
        logger.info("sendKafka Test");
        kafkaTemplate.send(topic, "sendKafka Test".getBytes());
    }

    public void rerun(String task_name, String at_time) {
        //只有停止的状态才能重置at_time
        TaskStatus taskStatus = statusTask(task_name);
        if (taskStatus != null && taskStatus.isStop()) {
            ScanServer scanServer = scanServerList.get(task_name);
            if (scanServer.resetAt_time(at_time)) {
                startTask(task_name);
            } else {
                logger.info("【{}】无法rerun，重置at_time失败", task_name);
            }
        } else {
            logger.info("【{}】无法rerun，当前状态：{}", task_name, JSON.toJSONString(taskStatus));
        }
    }
}

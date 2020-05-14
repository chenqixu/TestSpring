package com.cqx.syncos.task;

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

    public void init() {
        cacheServerList = new HashMap<>();
        taskInfoList = new HashMap<>();
        scanServerList = new HashMap<>();
        loadServerList = new HashMap<>();
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
        startTask(scanServer);
        scanServerList.put(task_name, scanServer);
        //生成加载服务并启动
        LoadServer loadServer = new LoadServer(cacheServer);
        loadServer.init(kafkaTemplate);
        startTask(loadServer);
        loadServerList.put(task_name, loadServer);
    }

    private void monitorTask() {

    }

    private void startTask(Runnable runnable) {
        new Thread(runnable).start();
    }

    public void startTask(String task_name) {
        for (Map.Entry<String, ScanServer> entry : scanServerList.entrySet()) {
            ScanServer scanServer = entry.getValue();
            if (scanServer.isThis(task_name) && scanServer.isClose()) {
                scanServer.resetFlag();
                startTask(scanServer);
                LoadServer loadServer = loadServerList.get(entry.getKey());
                loadServer.resetFlag();
                startTask(loadServer);
                break;
            }
        }
    }

    public void stopTask(String task_name) {
        for (Map.Entry<String, ScanServer> entry : scanServerList.entrySet()) {
            ScanServer scanServer = entry.getValue();
            if (scanServer.isThis(task_name)) {
                scanServer.close();
                LoadServer loadServer = loadServerList.get(entry.getKey());
                loadServer.close();
                break;
            }
        }
    }

    public TaskStatus statusTask(String task_name) {
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTask_name(task_name);
        boolean isFind = false;
        for (Map.Entry<String, ScanServer> entry : scanServerList.entrySet()) {
            ScanServer scanServer = entry.getValue();
            if (scanServer.isThis(task_name)) {
                taskStatus.setScan_isRun(!scanServer.isClose());
                LoadServer loadServer = loadServerList.get(entry.getKey());
                taskStatus.setLoad_isRun(!loadServer.isClose());
                isFind = true;
                break;
            }
        }
        if (isFind) return taskStatus;
        else return null;
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
}

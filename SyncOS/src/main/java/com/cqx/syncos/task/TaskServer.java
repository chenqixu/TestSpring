package com.cqx.syncos.task;

import com.cqx.syncos.task.bean.TaskInfo;
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
import java.util.List;

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

    private List<CacheServer> cacheServerList;
    private List<TaskInfo> taskInfoList;
    private List<ScanServer> scanServerList;
    private List<LoadServer> loadServerList;

    public void init() {
        cacheServerList = new ArrayList<>();
        taskInfoList = new ArrayList<>();
        scanServerList = new ArrayList<>();
        loadServerList = new ArrayList<>();
        //扫描目录、读取配置
        for (String table_path : FileUtil.listFile(data_path)) {
            addTask(table_path);
        }
    }

    public void addTask(String table_path) {
        CacheServer cacheServer = new CacheServer(data_path, table_path);
        //加入缓存服务列表
        cacheServerList.add(cacheServer);
        //加入任务列表
        taskInfoList.add(cacheServer.getTaskInfo());
        //生成扫描服务并启动
        ScanServer scanServer = new ScanServer(cacheServer);
        scanServer.init(jdbcTemplate);
        scanServer.start();
        scanServerList.add(scanServer);
        //生成加载服务并启动
        LoadServer loadServer = new LoadServer(cacheServer);
        loadServer.init(kafkaTemplate);
        loadServer.start();
        loadServerList.add(loadServer);
    }

    private void monitorTask() {

    }

    public void stopTask(String task_name) {
        for (ScanServer scanServer : scanServerList) {
            if (scanServer.isThis(task_name)) {
                scanServer.close();
                break;
            }
        }
        for (LoadServer loadServer : loadServerList) {
            if (loadServer.isThis(task_name)) {
                loadServer.close();
                break;
            }
        }
    }
}

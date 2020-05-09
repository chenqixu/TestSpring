package com.cqx.syncos.task;

import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.task.scan.ScanServer;
import com.cqx.syncos.util.DateUtil;
import com.cqx.syncos.util.FileUtil;
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

    private List<TaskInfo> taskInfoList;
    private List<ScanServer> scanServerList;

    public void init() {
        taskInfoList = new ArrayList<>();
        //扫描目录、读取配置
        for (String table_path : FileUtil.listFile(data_path)) {
            String full_table_path = FileUtil.endWith(data_path) + table_path;
            String full_table_scan_path = FileUtil.endWith(full_table_path) + "scan";
            //扫描读取配置
            String[] confFile = FileUtil.listFileEndWith(full_table_path, CACHE);
            if (confFile != null && confFile.length == 1) {
                TaskInfo taskInfo = FileUtil.readConfFile(FileUtil.endWith(full_table_path) + confFile[0], TaskInfo.class);
                //扫描缓存，更新上一次扫描时间
                String[] scanConfFile = FileUtil.listFileEndWith(full_table_scan_path, CACHE);
                if (scanConfFile != null && scanConfFile.length == 1) {
                    String at_time = FileUtil.readConfFile(FileUtil.endWith(full_table_scan_path) + scanConfFile[0]);
                    if (at_time != null && at_time.length() > 0) {
                        String update_at_time = DateUtil.format(Long.valueOf(at_time));
                        logger.info("{} 更新at_time：{}", taskInfo.getTask_name(), update_at_time);
                        taskInfo.setAt_time(update_at_time);
                    }
                }
                //加入任务列表
                taskInfoList.add(taskInfo);
            }
        }
        //生成任务
        createTask();
        //启动任务
        startTask();
        //任务监控
        monitorTask();
    }

    private void createTask() {
        scanServerList = new ArrayList<>();
        for (TaskInfo taskInfo : taskInfoList) {
            ScanServer scanServer = new ScanServer(taskInfo);
            scanServer.init(jdbcTemplate, data_path);
            scanServerList.add(scanServer);
        }
    }

    private void startTask() {
        for (ScanServer scanServer : scanServerList) {
            scanServer.start();
        }
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
    }
}

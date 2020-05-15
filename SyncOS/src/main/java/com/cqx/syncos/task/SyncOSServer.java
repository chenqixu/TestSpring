package com.cqx.syncos.task;

import com.alibaba.fastjson.JSON;
import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.task.bean.TaskStatus;
import com.cqx.syncos.util.file.FileUtil;
import com.cqx.syncos.util.kafka.SchemaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * SessionServer
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("/task")
public class SyncOSServer {
    private static final Logger logger = LoggerFactory.getLogger(SyncOSServer.class);

    @Value("${syncos.task.data.path}")
    private String data_path;

    @Resource
    private TaskServer taskServer;

    @PostConstruct
    private void init() {
        logger.info("任务初始化以及启动……");
        taskServer.init();
    }

    /**
     * 表名/scan/配置、缓存、扫描文件
     *
     * @param taskInfo
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public int addTask(@RequestBody TaskInfo taskInfo) {
        //创建对应任务目录，把bean转换成JSON串写入文件
        String table_path = FileUtil.endWith(data_path) + taskInfo.getTask_name();
        String conf_path = FileUtil.endWith(table_path) + taskInfo.getTask_name() + ".cache";
        String scan_path = FileUtil.endWith(table_path) + "scan";
        String load_path = FileUtil.endWith(table_path) + "load";
        String scan_cache_path = FileUtil.endWith(scan_path) + "scan.cache";
        String load_avsc_path = FileUtil.endWith(load_path) + taskInfo.getTask_name() + ".avsc";
        //判断表目录是否存在
        boolean isExists = FileUtil.isExists(table_path);
        logger.info("参数：{}", JSON.toJSON(taskInfo));
        if (!isExists) {
            //创建表目录
            FileUtil.mkDir(table_path);
            //保存配置到表目录
            FileUtil.saveConfToFile(conf_path, JSON.toJSON(taskInfo).toString());
            //创建扫描目录
            FileUtil.mkDir(scan_path);
            //创建加载目录
            FileUtil.mkDir(load_path);
//            //初始化扫描缓存
//            FileUtil.saveConfToFile(scan_cache_path, String.valueOf(DateUtil.format(taskInfo)));
            //初始化avro，并保存
            SchemaUtil schemaUtil = new SchemaUtil(taskInfo);
            FileUtil.saveConfToFile(load_avsc_path, schemaUtil.getSchemaStr());
            //生成并启动任务
            taskServer.addTask(taskInfo.getTask_name());
        } else {
            logger.info("{} 已经存在，不用创建。", data_path);
        }
        return 0;
    }

    @RequestMapping(value = "/stop/{task_name}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public int stopTask(@PathVariable String task_name) {
        taskServer.stopTask(task_name);
        return 0;
    }

    @RequestMapping(value = "/start/{task_name}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public int startTask(@PathVariable String task_name) {
        taskServer.startTask(task_name);
        return 0;
    }

    @RequestMapping(value = "/stopAll", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public int stopAllTask() {
        taskServer.stopAll();
        return 0;
    }

    @RequestMapping(value = "/status/{task_name}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskStatus getTaskStatus(@PathVariable String task_name) {
        return taskServer.statusTask(task_name);
    }

    @RequestMapping(value = "/statusAll", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskStatus> getAllTaskStatus() {
        return taskServer.statusAllTask();
    }

    @RequestMapping(value = "/sendKafka/{topic}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendKafka(@PathVariable String topic) {
        taskServer.sendKafka(topic);
    }

    @RequestMapping(value = "/rerun/{task_name}/{at_time}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void rerun(@PathVariable String task_name, @PathVariable String at_time) {
        taskServer.rerun(task_name, at_time);
    }
}

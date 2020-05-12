package com.cqx.syncos.task.cache;

import com.cqx.syncos.task.bean.LoadBean;
import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.util.DateUtil;
import com.cqx.syncos.util.file.FileUtil;
import com.cqx.syncos.util.kafka.GenericRecordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存服务
 *
 * @author chenqixu
 */
public class CacheServer {

    private static final Logger logger = LoggerFactory.getLogger(CacheServer.class);
    private TaskInfo taskInfo;
    private LoadBean loadBean;
    private String scan_cache_path;
    private String scan_save_file;
    private String load_cache_path;
    private GenericRecordUtil genericRecordUtil;

    public CacheServer(String data_path, String table_name) {
        init(data_path, table_name);
    }

    private void init(String data_path, String table_name) {
        //路径
        String table_path = FileUtil.endWith(data_path) + table_name;
        String table_cache_path = FileUtil.endWith(table_path) + table_name + ".cache";
        String scan_path = FileUtil.endWith(table_path) + "scan";
        scan_cache_path = FileUtil.endWith(scan_path) + "scan.cache";
        scan_save_file = FileUtil.endWith(scan_path) + "res000001.log";
        String load_path = FileUtil.endWith(table_path) + "load";
        load_cache_path = FileUtil.endWith(load_path) + "load.cache";
        String load_avsc_path = FileUtil.endWith(load_path) + table_name + ".avsc";
        //读取表任务配置
        taskInfo = FileUtil.readConfFile(table_cache_path, TaskInfo.class);
        //判断是否有扫描缓存，有值就读取缓存，更新at_time到taskInfo
        if (FileUtil.isExists(scan_cache_path)) {
            String at_time = FileUtil.readConfFile(scan_cache_path);
            if (at_time != null && at_time.length() > 0) {
                String update_at_time = DateUtil.format(Long.valueOf(at_time));
                logger.info("{} 更新at_time：{}", taskInfo.getTask_name(), update_at_time);
                taskInfo.setAt_time(update_at_time);
            }
        }
        //判断是否有加载缓存，有值就读取缓存
        if (FileUtil.isExists(load_cache_path)) {
            loadBean = FileUtil.readConfFile(load_cache_path, LoadBean.class);
        }
        //初始化avro工具
        genericRecordUtil = new GenericRecordUtil(taskInfo);
    }

    public void updateScanCache(String data) {
        //更新at_time到文件
        FileUtil.saveConfToFile(scan_cache_path, data);
    }

    public void updateLoadCache(LoadBean loadBean) {
        FileUtil.saveConfToFile(load_cache_path, loadBean);
        this.loadBean = loadBean;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public String getSaveFile() {
        return scan_save_file;
    }

    public GenericRecordUtil getGenericRecordUtil() {
        return genericRecordUtil;
    }
}

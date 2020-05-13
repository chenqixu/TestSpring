package com.cqx.syncos.task.cache;

import com.cqx.syncos.task.bean.LoadBean;
import com.cqx.syncos.task.bean.ScanCache;
import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.util.DateUtil;
import com.cqx.syncos.util.TimeCostUtil;
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
    private ScanCache scanCache;
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
        //判断是否有表任务配置
        if (FileUtil.isExists(table_cache_path)) {
            //读取表任务配置
            taskInfo = FileUtil.readConfFile(table_cache_path, TaskInfo.class);
        }
        //判断是否有扫描缓存，有值就读取缓存，更新at_time到taskInfo
        if (FileUtil.isExists(scan_cache_path)) {
            scanCache = FileUtil.readConfFile(scan_cache_path, ScanCache.class);
//            if (at_time != null && at_time.length() > 0) {
//                String update_at_time = DateUtil.format(Long.valueOf(at_time));
//                logger.info("{} 更新at_time：{}", taskInfo.getTask_name(), update_at_time);
//                taskInfo.setAt_time(update_at_time);
//            }
        }
        //判断是否有加载缓存，有值就读取缓存
        if (FileUtil.isExists(load_cache_path)) {
            loadBean = FileUtil.readConfFile(load_cache_path, LoadBean.class);
        }
        //判断是否有avsc文件
        if (FileUtil.isExists(load_avsc_path)) {
            //初始化avro工具
            genericRecordUtil = new GenericRecordUtil(taskInfo);
        }
    }

    public void updateScanCache(String data) {
        //更新at_time到文件
        FileUtil.saveConfToFile(scan_cache_path, data);
    }

    public void updateScanCache(ScanCache scanCache) {
        //更新at_time和写入位置到文件
        FileUtil.saveConfToFile(scan_cache_path, scanCache);
    }

    private int last_header_pos_next = 0;
    private TimeCostUtil timeCostUtil = new TimeCostUtil();

    public void updateLoadCache(int header_pos_next, boolean force) {
        last_header_pos_next = header_pos_next;
        if (timeCostUtil.tag(5000) || force) {
            logger.info("加载【{}】updateLoadCache，header_pos_next：{}", taskInfo.getDst_name(), header_pos_next);
            loadBean = new LoadBean(getSaveFile(), header_pos_next);
            FileUtil.saveConfToFileByThread(load_cache_path, loadBean);
        }
    }

    public void updateLoadCache(int header_pos_next) {
        updateLoadCache(header_pos_next, false);
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

    public LoadBean getLoadBean() {
        return loadBean;
    }

    public ScanCache getScanCache() {
        return scanCache;
    }
}

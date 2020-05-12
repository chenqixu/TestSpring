package com.cqx.syncos.task.scan;

import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.task.cache.CacheServer;
import com.cqx.syncos.util.DateUtil;
import com.cqx.syncos.util.SleepUtil;
import com.cqx.syncos.util.TimeCostUtil;
import com.cqx.syncos.util.db.DBFormatEnum;
import com.cqx.syncos.util.db.DBFormatUtil;
import com.cqx.syncos.util.file.RAFFileMangerCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 扫描任务
 * <pre>
 *     定时扫描
 *     1、初始化，从缓存读取上一次处理时间到内存中。
 *     2、判断当前系统时间是否大于等于上一次处理时间+间隔时间：
 *     * 如果条件满足，则跳转到步骤3；
 *     * 如果条件不满足，则跳转到步骤5。
 *     3、抽取更新时间字段在上一次处理时间和上一次处理时间+间隔时间的数据，文件落地。
 *     4、更新上一次处理时间为上一次处理时间+间隔时间。
 *     5、休眠，然后跳转到步骤2。
 * </pre>
 *
 * @author chenqixu
 */
public class ScanServer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ScanServer.class);
    private volatile boolean flag = true;
    private TaskInfo taskInfo;
    private TimeCostUtil timeCostUtil;
    private JdbcTemplate jdbcTemplate;
    private String at_time;//上一次处理时间
    private CacheServer cacheServer;
    //    private FileMangerCenter fileMangerCenter;
    private RAFFileMangerCenter rafFileMangerCenter;
    private String value_split;

    public ScanServer(CacheServer cacheServer) {
        this(cacheServer.getTaskInfo());
        this.cacheServer = cacheServer;
    }

    private ScanServer(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        this.timeCostUtil = new TimeCostUtil();
        //传入上一次处理时间
        this.at_time = taskInfo.getAt_time();
        this.value_split = taskInfo.getScan_split().replace("\\", "");
    }

    public void init(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
//        fileMangerCenter = new FileMangerCenter(cacheServer);
        try {
            rafFileMangerCenter = new RAFFileMangerCenter(cacheServer);
//            fileMangerCenter.initWriter();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        while (flag) {
            //获取当前时间，判断当前时间是否大于等于上一次处理时间+间隔
            if (timeCostUtil.tag(getAt_time(), taskInfo.getInterval())) {
                logger.info("{} 开始扫描……", taskInfo.getTask_name());
                //查询
                List<Map<String, Object>> queryList = jdbcTemplate.queryForList(getQuerySQL());
                //查询结果写入文件，按批写，达到大小就换一个文件
                String[] fields = taskInfo.getSrc_fields().split(",", -1);
                StringBuilder sb;
                for (int i = 0; i < queryList.size(); i++) {
                    sb = new StringBuilder();
                    for (int j = 0; j < fields.length; j++) {
                        if (j == fields.length - 1) {
                            sb.append(queryList.get(i).get(fields[j]));
                        } else {
                            sb.append(queryList.get(i).get(fields[j])).append(value_split);
                        }
                    }
                    try {
//                        if (i == queryList.size() - 1) {//最后一条强制刷新
//                            fileMangerCenter.write(sb.toString(), true);
//                        } else {
//                            fileMangerCenter.write(sb.toString());
//                        }
                        rafFileMangerCenter.write(sb.toString());
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                //修改at_time
                at_time = getNextAt_time();
                //更新at_time到文件
                cacheServer.updateScanCache(String.valueOf(getAt_time()));
            }
            SleepUtil.sleepMilliSecond(200);
        }
        //资源释放
        try {
//            fileMangerCenter.close();
            rafFileMangerCenter.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("扫描任务停止：{}", taskInfo.getTask_name());
    }

    private String getQuerySQL() {
        StringBuilder sb = new StringBuilder();
        sb.append("select ")
                .append(taskInfo.getSrc_fields())
                .append(" from ")
                .append(taskInfo.getSrc_name())
                .append(" where ")
                .append(taskInfo.getModify_time_fields())
                .append(" between ")
                .append(DBFormatUtil.to_date(addSingleQuotationMark(at_time), DBFormatEnum.YYYYMMDDHH24MISS))
                .append(" and ")
                .append(DBFormatUtil.to_date(addSingleQuotationMark(at_time), DBFormatEnum.YYYYMMDDHH24MISS))
                .append(" + ")
                .append(taskInfo.getInterval())//单位是毫秒
                .append("/(24*60*60*1000) ");
        logger.info("sql：{}", sb.toString());
        return sb.toString();
    }

    private String addSingleQuotationMark(String field) {
        return "'" + field + "'";
    }

    private long getAt_time() {
        return DateUtil.format(at_time);
    }

    private String getNextAt_time() {
        return DateUtil.format(getAt_time() + taskInfo.getInterval());
    }

    public boolean isThis(String task_name) {
        return taskInfo.getTask_name().equals(task_name);
    }

    public void close() {
        flag = false;
    }
}

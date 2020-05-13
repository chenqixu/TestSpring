package com.cqx.syncos.task.scan;

import com.cqx.syncos.task.bean.ScanCache;
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
import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class ScanServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ScanServer.class);
    private volatile boolean flag = true;
    private TaskInfo taskInfo;
    private TimeCostUtil timeCostUtil;
    private JdbcTemplate jdbcTemplate;
    private String at_time;//上一次处理时间
    private CacheServer cacheServer;
    private RAFFileMangerCenter rafFileMangerCenter;
    private String value_split;
    private TimeCostUtil runCost;
    private Long scanSeq = 0L;
    private ScanCache scanCache;

    public ScanServer(CacheServer cacheServer) {
        this(cacheServer.getTaskInfo());
        this.cacheServer = cacheServer;
        this.scanCache = cacheServer.getScanCache();
    }

    private ScanServer(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        this.timeCostUtil = new TimeCostUtil();
        this.runCost = new TimeCostUtil();
        if (taskInfo != null) {
            //分隔符处理
            this.value_split = taskInfo.getScan_split().replace("\\", "");
        }
    }

    private String logHeader() {
        return String.format("扫描【%s】", taskInfo.getTask_name());
    }

    public void init(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        if (scanCache != null && rafFileMangerCenter != null) {//有缓存，at_time从缓存获取
            at_time = DateUtil.format(scanCache.getAt_time());
            rafFileMangerCenter.setHeader_pos(scanCache.getHeader_pos_next());
            logger.info("{}有scanCache缓存，at_time从缓存获取：{}，文件写入位置：{}", logHeader(), at_time, scanCache.getHeader_pos_next());
        } else if (taskInfo != null) {//没有缓存，at_time从taskInfo获取
            at_time = taskInfo.getAt_time();
            logger.info("{}没有scanCache缓存，at_time从taskInfo获取：{}", logHeader(), at_time);
        }
    }

    @Override
    public void run() {
        logger.info("{}任务启动", logHeader());
        //资源初始化
        resourceInit();
        while (flag) {
            //获取当前时间，判断当前时间是否大于等于上一次处理时间+间隔
            if (timeCostUtil.tag(getAt_time(), taskInfo.getInterval())) {
                runCost.start();
                logger.info("{}扫描序号：{}，开始扫描", logHeader(), increaseAndGetScanSeq());
                //查询
                //查询结果写入文件，按批写，达到大小就换一个文件
                String[] fields = taskInfo.getSrc_fields().split(",", -1);
                int scan_query_cnt = query(getQuerySQL(), fields.length, value_split);
                //修改at_time
                at_time = getNextAt_time();
                //更新at_time和文件写入位置到文件
                scanCache = new ScanCache(getAt_time(), rafFileMangerCenter.getHeader_pos_next());
                cacheServer.updateScanCache(scanCache);
                runCost.end();
                logger.info("{}扫描序号：{}，扫描结束，扫描耗时：{}，扫描结果：{}", logHeader(), getScanSeq(), runCost.getCost(), scan_query_cnt);
            }
            SleepUtil.sleepMilliSecond(200);
        }
        //资源释放
        release();
        logger.info("{}扫描任务停止", logHeader());
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
        logger.info("{}扫描序号：{}，sql：{}", logHeader(), getScanSeq(), sb.toString());
        return sb.toString();
    }

    int query(String sql, int fieldNum, String value_split) {
        int[] queryNum = {0};
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                queryNum[0]++;
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= fieldNum; i++) {
                    if (i == fieldNum) {
                        sb.append(resultSet.getObject(i));
                    } else {
                        sb.append(resultSet.getObject(i)).append(value_split);
                    }
                }
                if (sb.length() > 0) {
                    logger.debug("{}内容：{}", logHeader(), sb.toString());
                    try {
                        rafFileMangerCenter.write(sb.toString());
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });
        return queryNum[0];
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

    private long increaseAndGetScanSeq() {
        scanSeq++;
        return getScanSeq();
    }

    private long getScanSeq() {
        return scanSeq;
    }

    public boolean isThis(String task_name) {
        return taskInfo.getTask_name().equals(task_name);
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

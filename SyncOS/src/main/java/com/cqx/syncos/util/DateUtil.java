package com.cqx.syncos.util;

import com.cqx.syncos.task.bean.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtil
 *
 * @author chenqixu
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static long format(TaskInfo taskInfo) {
        return format(taskInfo.getAt_time());
    }

    public static long format(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            logger.error("初始化扫描缓存异常，时间格式错误：" + time, e);
            throw new NullPointerException("初始化扫描缓存异常，时间格式错误：" + time);
        }
    }

    public static String format(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}

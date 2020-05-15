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

    public static long format(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            throw new NullPointerException(String.format("时间格式错误，格式：%s，内容：%s", pattern, time));
        }
    }

    public static long format(String time) {
        return format(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static long atTimeCheck(String time) {
        return format(time, "yyyyMMddHHmmss");
    }

    public static String format(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}

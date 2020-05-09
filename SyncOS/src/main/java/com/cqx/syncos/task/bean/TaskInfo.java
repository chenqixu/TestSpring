package com.cqx.syncos.task.bean;

import java.util.Map;

/**
 * TaskInfo
 *
 * @author chenqixu
 */
public class TaskInfo {
    private String task_name;//任务名
    private long interval;//扫描间隔（毫秒）1*1000/(24*60*60*1000)
    private String scan_split;//扫描文件分隔字符串
    private String at_time;//上一次扫描时间（第一次扫描时间=at_time+interval）

    private String src_type;//源类型oracle、mysql、kafka
    private String src_name;//源表名
    private String src_fields;//源字段
    private String modify_time_fields;//时间变更字段
    private Map<String, String> src_conf;//源配置，oracle：tns、user、passwd；kafka：bootstrap.servers等

    private String dst_type;//目标类型oracle、mysql、kafka
    private String dst_name;//目标表名(kafka就是话题名)
    private String dst_fields;//目标字段(kafka就不用填，会根据源字段生成对应的avsc
    private Map<String, String> dst_conf;//目标配置，oracle：tns、user、passwd；kafka：bootstrap.servers等

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getSrc_type() {
        return src_type;
    }

    public void setSrc_type(String src_type) {
        this.src_type = src_type;
    }

    public String getSrc_name() {
        return src_name;
    }

    public void setSrc_name(String src_name) {
        this.src_name = src_name;
    }

    public String getSrc_fields() {
        return src_fields;
    }

    public void setSrc_fields(String src_fields) {
        this.src_fields = src_fields;
    }

    public Map<String, String> getSrc_conf() {
        return src_conf;
    }

    public void setSrc_conf(Map<String, String> src_conf) {
        this.src_conf = src_conf;
    }

    public String getDst_type() {
        return dst_type;
    }

    public void setDst_type(String dst_type) {
        this.dst_type = dst_type;
    }

    public String getDst_name() {
        return dst_name;
    }

    public void setDst_name(String dst_name) {
        this.dst_name = dst_name;
    }

    public String getDst_fields() {
        return dst_fields;
    }

    public void setDst_fields(String dst_fields) {
        this.dst_fields = dst_fields;
    }

    public Map<String, String> getDst_conf() {
        return dst_conf;
    }

    public void setDst_conf(Map<String, String> dst_conf) {
        this.dst_conf = dst_conf;
    }

    public String getModify_time_fields() {
        return modify_time_fields;
    }

    public void setModify_time_fields(String modify_time_fields) {
        this.modify_time_fields = modify_time_fields;
    }

    public String getAt_time() {
        return at_time;
    }

    public void setAt_time(String at_time) {
        this.at_time = at_time;
    }

    public String getScan_split() {
        return scan_split;
    }

    public void setScan_split(String scan_split) {
        this.scan_split = scan_split;
    }
}

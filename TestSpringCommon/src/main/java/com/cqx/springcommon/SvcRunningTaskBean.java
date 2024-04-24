package com.cqx.springcommon;

import java.sql.Date;

/**
 * label_running_task
 *
 * @author chenqixu
 */
public class SvcRunningTaskBean {
    private String task_id;// varchar2(32) PRIMARY KEY--任务id(可以是蚂蚁的实例id)
    private String task_desc;// 任务描述
    private String channel_id;// varchar2(20)--渠道id(区分来源)
    private String task_class;// varchar2(200)--任务执行类(区分是什么任务,控制层,抽取上传,合并,衍生计算,……)
    private String task_commands;// varchar2(4000)--任务参数
    private Date create_time;// date--创建时间
    private Date run_time;// date--运行时间
    private Date redo_time;// date--延期运行时间
    private Date done_time;// date--完成时间
    private String task_status;// varchar2(10)--任务状态(wait,run,done,error)
    private int task_redo_cnt;// int--任务重试次数
    private String error_msg;// varchar2(4000)--错误信息
    private String exec_ip;// varchar2(20)--执行容器ip
    private String exec_pid;// varchar2(20)--执行容器进程
    private Date exec_heart_time;// date--执行容器心跳时间
    private String queue_name;// 队列名称
    private int req_source = 1;// 任务资源

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getTask_class() {
        return task_class;
    }

    public void setTask_class(String task_class) {
        this.task_class = task_class;
    }

    public String getTask_commands() {
        return task_commands;
    }

    public void setTask_commands(String task_commands) {
        this.task_commands = task_commands;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getRun_time() {
        return run_time;
    }

    public void setRun_time(Date run_time) {
        this.run_time = run_time;
    }

    public Date getDone_time() {
        return done_time;
    }

    public void setDone_time(Date done_time) {
        this.done_time = done_time;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public int getTask_redo_cnt() {
        return task_redo_cnt;
    }

    public void setTask_redo_cnt(int task_redo_cnt) {
        this.task_redo_cnt = task_redo_cnt;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getExec_ip() {
        return exec_ip;
    }

    public void setExec_ip(String exec_ip) {
        this.exec_ip = exec_ip;
    }

    public String getExec_pid() {
        return exec_pid;
    }

    public void setExec_pid(String exec_pid) {
        this.exec_pid = exec_pid;
    }

    public Date getExec_heart_time() {
        return exec_heart_time;
    }

    public void setExec_heart_time(Date exec_heart_time) {
        this.exec_heart_time = exec_heart_time;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getQueue_name() {
        return queue_name;
    }

    public void setQueue_name(String queue_name) {
        this.queue_name = queue_name;
    }

    public int getReq_source() {
        return req_source;
    }

    public void setReq_source(int req_source) {
        this.req_source = req_source;
    }

    public Date getRedo_time() {
        return redo_time;
    }

    public void setRedo_time(Date redo_time) {
        this.redo_time = redo_time;
    }
}

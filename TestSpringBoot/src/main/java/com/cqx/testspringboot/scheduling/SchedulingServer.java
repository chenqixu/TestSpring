package com.cqx.testspringboot.scheduling;

import com.alibaba.fastjson.JSON;
import com.cqx.common.utils.http.HttpUtil;
import com.cqx.testspringboot.activiti.dao.CommonBaseDao;
import com.cqx.testspringboot.scheduling.model.SchedulingResultBean;
import com.cqx.testspringboot.scheduling.model.SvcRunningTaskBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 调度服务
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("${cmcc.web.servlet-path}/scheduling")
public class SchedulingServer {
    private static final Logger logger = LoggerFactory.getLogger(SchedulingServer.class);

    @Resource(name = "com.cqx.testspringboot.activiti.dao.CommonBaseDao")
    protected CommonBaseDao dao;

    protected HttpUtil httpUtil;
    protected String rm_url;
    protected String nm_url;

    @PostConstruct
    private void init() {
        // 从公共配置管理服务获取RM和NM地址
        httpUtil = new HttpUtil();
    }

    /**
     * 定时扫描任务表
     */
    @RequestMapping("/scanTask")
    public void scanTask() {
        rm_url = httpUtil.doPost("http://127.0.0.1:19090/nl-edc-cct-sys-ms-dev/v1/config/queryUrlConfigByName", "rm");
        nm_url = httpUtil.doPost("http://127.0.0.1:19090/nl-edc-cct-sys-ms-dev/v1/config/queryUrlConfigByName", "nm");
        logger.info("rm={}", rm_url);
        logger.info("nm={}", nm_url);
        // 定时扫描任务表（需要执行的任务，有些超时的任务也需要清理）
        // 先判断任务状态
        // 再判断心跳，心跳有没什么办法处理？
        List<SvcRunningTaskBean> taskList = dao.query("select task_id,task_desc,channel_id,task_class,task_commands " +
                        " from svc_running_task where task_status='wait' order by channel_id,create_time"
                , SvcRunningTaskBean.class);
        logger.info("task.size={}", taskList);
        for (SvcRunningTaskBean taskBean : taskList) {
            logger.info("获取到待执行任务, 任务ID={}, 任务描述={}", taskBean.getTask_id(), taskBean.getTask_desc());
            // 方案1：提交给RM，由RM确认是否有资源，如果有资源，则由RM转发给NM执行，如果没有则返回失败
            // 方案2：提交给RM咨询是否有资源，有资源情况下需要预占，然后提交给NM执行
            String execRet = httpUtil.doPost(nm_url + "/exec", JSON.toJSONString(taskBean));
            SchedulingResultBean resultBean = JSON.parseObject(execRet, SchedulingResultBean.class);
            logger.info("提交结果={}，任务ID={}", resultBean.getRes_code(), resultBean.getTask_id());
        }
    }

    @RequestMapping("/addTask")
    public void addTask() {
        // 创建任务的接口，写到running_task

    }
}

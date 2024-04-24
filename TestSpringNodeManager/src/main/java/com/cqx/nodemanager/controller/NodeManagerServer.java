package com.cqx.nodemanager.controller;

import com.alibaba.fastjson.JSON;
import com.cqx.common.utils.http.HttpUtil;
import com.cqx.common.utils.net.IpUtil;
import com.cqx.nodemanager.task.ITask;
import com.cqx.springcommon.NodeReqBean;
import com.cqx.springcommon.SchedulingResultBean;
import com.cqx.springcommon.SvcRunningTaskBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器管理器
 * <pre>
 *     先获取资源管理器地址
 *     启动需要向资源管理器进行注册，明示自身有几个空闲容器
 *     停止需要向资源管理器发起离线申请
 *     平时需要向资源管理器发起心跳
 *     如果异常挂掉，资源管理器那边判断心跳超时，要重新注册
 * </pre>
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("/nm")
public class NodeManagerServer {
    private static final Logger logger = LoggerFactory.getLogger(NodeManagerServer.class);
    //    private HashMap<String, Thread> threadMaps = new HashMap<>();
    private ConcurrentHashMap<String, String> containerStatus = new ConcurrentHashMap<>();
    private HttpUtil httpUtil = new HttpUtil();

    @PostConstruct
    private void init() {
        NodeReqBean nodeReqBean = new NodeReqBean();
        nodeReqBean.setNode_ip(IpUtil.getIp());
        nodeReqBean.setNode_name(IpUtil.getHostName());
        nodeReqBean.setNode_source(5);
        // 向rm注册资源
        String nodeRegisterRet = httpUtil.doPost("http://localhost:10801/resource-manager/rm/nodeRegister", JSON.toJSONString(nodeReqBean));
        logger.info("[向rm注册资源]{}", nodeRegisterRet);
        //todo 注册失败？
    }

    private void heartReport() {
    }

    @RequestMapping(value = "/exec", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public SchedulingResultBean nodeExecTask(@RequestBody SvcRunningTaskBean svcRunningTaskBean) {
        // 启动任务
        int ret = createContainer(svcRunningTaskBean);

        SchedulingResultBean srb = new SchedulingResultBean();
        srb.setTask_id(svcRunningTaskBean.getTask_id());
        srb.setRes_code(ret);
        srb.setBody(ret == 1 ? "submit task success. task_id is " + srb.getTask_id() : "submit task fail.");
        if (ret == 1) {
            // 更新任务状态
            svcRunningTaskBean.setTask_status("running");
            String taskStatusUpdateRet = httpUtil.doPost("http://localhost:10801/resource-manager/rm/updateTaskStatus", JSON.toJSONString(svcRunningTaskBean));
            logger.info("[更新任务状态]={}", taskStatusUpdateRet);
        }
        return srb;
    }

    /**
     * 查询容器里的任务运行状态
     *
     * @param task_id
     * @return
     */
    @RequestMapping(value = "/queryContainerStatus/{task_id}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryContainerStatus(@PathVariable String task_id) {
        String status = containerStatus.get(task_id);
        return (status == null || status.length() == 0) ? "任务不存在" : status;
    }

    /**
     * 查询Node状态
     *
     * @return
     */
    @RequestMapping(value = "/queryNodeManagerStatus", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryNodeManagerStatus() {
        return "ok";
    }

    private int createContainer(SvcRunningTaskBean svcRunningTaskBean) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                containerStatus.put(svcRunningTaskBean.getTask_id(), "running");
                logger.info("[{}] start.", svcRunningTaskBean.getTask_id());

                // 根据task_class进行反射调用
                String task_class = svcRunningTaskBean.getTask_class();
                String task_desc = svcRunningTaskBean.getTask_desc();
                String task_commands = svcRunningTaskBean.getTask_commands();
                ITask iTask = null;
                try {
                    Class cls = Class.forName(task_class);
                    iTask = (ITask) cls.newInstance();
                } catch (ClassNotFoundException e) {
                    logger.error(String.format("==%s【找不到类】，异常信息：%s",
                            task_desc, e.getMessage()), e);
                } catch (IllegalAccessException | InstantiationException e) {
                    logger.error(String.format("==%s【反射异常】，异常信息：%s",
                            task_desc, e.getMessage()), e);
                }
                if (iTask != null) {
                    try {
                        Map params = JSON.parseObject(task_commands, Map.class);
                        // 初始化
                        iTask.init(params);
                        // 执行
                        iTask.run();
                    } catch (Throwable e) {
                        logger.error(String.format("==%s【初始化异常】，异常信息：%s",
                                task_desc, e.getMessage()), e);
                    } finally {
                        // 资源释放
                        iTask.release();
                    }
                }
                logger.info("[{}] end.", svcRunningTaskBean.getTask_id());
                // 修改任务状态end & 通知rm资源释放
                svcRunningTaskBean.setTask_status("end");
                String taskStatusUpdateRet = httpUtil.doPost("http://localhost:10801/resource-manager/rm/updateTaskStatus", JSON.toJSONString(svcRunningTaskBean));
                logger.info("[修改任务状态end.更新任务状态]={}", taskStatusUpdateRet);
                containerStatus.put(svcRunningTaskBean.getTask_id(), "end");
            }
        });
        thread.setDaemon(true);
        thread.start();
//        threadMaps.put(svcRunningTaskBean.getTask_id(), thread);
        return 1;
    }
}

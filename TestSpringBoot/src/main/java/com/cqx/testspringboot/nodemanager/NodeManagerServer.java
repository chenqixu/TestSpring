package com.cqx.testspringboot.nodemanager;

import com.alibaba.fastjson.JSON;
import com.cqx.common.utils.system.SleepUtil;
import com.cqx.testspringboot.common.SpringUtils;
import com.cqx.testspringboot.scheduling.model.SchedulingResultBean;
import com.cqx.testspringboot.scheduling.model.SvcRunningTaskBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
@RequestMapping("${cmcc.web.servlet-path}/nm")
public class NodeManagerServer {
    private static final Logger logger = LoggerFactory.getLogger(NodeManagerServer.class);
    private HashMap<String, Thread> threadMaps = new HashMap<>();
    private int queueMaxSize = 5;

    @PostConstruct
    private void init() {
        logger.info("启动生命周期监控线程.");
        lifeMonitor();
    }

    @RequestMapping(value = "/exec", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public SchedulingResultBean nodeExecTask(@RequestBody SvcRunningTaskBean svcRunningTaskBean) {
        // 启动任务
        int ret = createContainer(svcRunningTaskBean);

        SchedulingResultBean srb = new SchedulingResultBean();
        srb.setTask_id(svcRunningTaskBean.getTask_id());
        srb.setRes_code(ret);
        srb.setBody(ret == 0 ? "submit task success. task_id is " + srb.getTask_id() : "submit task fail.");
        return srb;
    }

    private int createContainer(SvcRunningTaskBean svcRunningTaskBean) {
        if (threadMaps.size() == queueMaxSize) {
            logger.warn("队列已满，无法执行！");
            return 1;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("[{}] start.", svcRunningTaskBean.getTask_id());

                // 根据task_class进行反射调用
                String task_class = svcRunningTaskBean.getTask_class();
                String task_desc = svcRunningTaskBean.getTask_desc();
                String task_commands = svcRunningTaskBean.getTask_commands();
                ITask iTask = null;
//                try {
                    Object beanObj = SpringUtils.getBean(task_class);
                    iTask = (ITask) beanObj;
//                    Class cls = Class.forName(task_class);
//                    iTask = (ITask) cls.newInstance();
//                } catch (ClassNotFoundException e) {
//                    logger.error(String.format("==%s【找不到类】，异常信息：%s",
//                            task_desc, e.getMessage()), e);
//                } catch (IllegalAccessException | InstantiationException e) {
//                    logger.error(String.format("==%s【反射异常】，异常信息：%s",
//                            task_desc, e.getMessage()), e);
//                }
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
            }
        });
        thread.setDaemon(true);
        thread.start();
        threadMaps.put(svcRunningTaskBean.getTask_id(), thread);
        return 0;
    }

    private void lifeMonitor() {
        Thread lifeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Iterator<Thread> it = threadMaps.values().iterator();
                    while (it.hasNext()) {
                        Thread _thread = it.next();
                        if (_thread.getState().equals(Thread.State.TERMINATED)) {
                            logger.info("[remove] {}-{} status={}", _thread.getId(), _thread.getName(), _thread.getState());
                            it.remove();
                            it = threadMaps.values().iterator();
                        }
                    }
                    SleepUtil.sleepMilliSecond(500L);
                }
            }
        });
        lifeThread.setDaemon(true);
        lifeThread.start();
    }
}

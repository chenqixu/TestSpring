package com.cqx.nodemanager.controller;

//import com.alibaba.fastjson.JSON;
//import com.cqx.common.utils.http.HttpUtil;
//import com.cqx.common.utils.system.SleepUtil;
//import com.cqx.nodemanager.task.ITask;
//import com.cqx.springcommon.SchedulingResultBean;
//import com.cqx.springcommon.SvcRunningTaskBean;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;

/**
 * 备份
 *
 * @author chenqixu
 */
public class NodeManagerServer2 {
//    private static final Logger logger = LoggerFactory.getLogger(NodeManagerServer.class);
//    private HashMap<String, Thread> threadMaps = new HashMap<>();
//    private int queueMaxSize = 5;
//    private HttpUtil httpUtil = new HttpUtil();
//
//    @PostConstruct
//    private void init() {
//        lifeMonitor();
//    }
//
//    private void heartReport() {
//
//    }
//
//    @RequestMapping(value = "/exec", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//    public SchedulingResultBean nodeExecTask(@RequestBody SvcRunningTaskBean svcRunningTaskBean) {
//        // 启动任务
//        int ret = createContainer(svcRunningTaskBean);
//
//        SchedulingResultBean srb = new SchedulingResultBean();
//        srb.setTask_id(svcRunningTaskBean.getTask_id());
//        srb.setRes_code(ret);
//        srb.setBody(ret == 0 ? "submit task success. task_id is " + srb.getTask_id() : "submit task fail.");
//        if (ret == 0) {
//            // 更新任务状态
//            svcRunningTaskBean.setTask_status("running");
//            String taskStatusUpdateRet = httpUtil.doPost("http://localhost:10801/resource-manager/rm/updateTaskStatus", JSON.toJSONString(svcRunningTaskBean));
//            logger.info("[更新任务状态]={}", taskStatusUpdateRet);
//        }
//        return srb;
//    }
//
//    private int createContainer(SvcRunningTaskBean svcRunningTaskBean) {
//        if (threadMaps.size() == queueMaxSize) {
//            logger.warn("队列已满，无法执行！");
//            return 1;
//        }
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                logger.info("[{}] start.", svcRunningTaskBean.getTask_id());
//
//                // 根据task_class进行反射调用
//                String task_class = svcRunningTaskBean.getTask_class();
//                String task_desc = svcRunningTaskBean.getTask_desc();
//                String task_commands = svcRunningTaskBean.getTask_commands();
//                ITask iTask = null;
//                try {
//                    Class cls = Class.forName(task_class);
//                    iTask = (ITask) cls.newInstance();
//                } catch (ClassNotFoundException e) {
//                    logger.error(String.format("==%s【找不到类】，异常信息：%s",
//                            task_desc, e.getMessage()), e);
//                } catch (IllegalAccessException | InstantiationException e) {
//                    logger.error(String.format("==%s【反射异常】，异常信息：%s",
//                            task_desc, e.getMessage()), e);
//                }
//                if (iTask != null) {
//                    try {
//                        Map params = JSON.parseObject(task_commands, Map.class);
//                        // 初始化
//                        iTask.init(params);
//                        // 执行
//                        iTask.run();
//                    } catch (Throwable e) {
//                        logger.error(String.format("==%s【初始化异常】，异常信息：%s",
//                                task_desc, e.getMessage()), e);
//                    } finally {
//                        // 资源释放
//                        iTask.release();
//                    }
//                }
//                logger.info("[{}] end.", svcRunningTaskBean.getTask_id());
//                // 修改任务状态end & 通知rm资源释放
//                svcRunningTaskBean.setTask_status("end");
//                String taskStatusUpdateRet = httpUtil.doPost("http://localhost:10801/resource-manager/rm/updateTaskStatus", JSON.toJSONString(svcRunningTaskBean));
//                logger.info("[修改任务状态end.更新任务状态]={}", taskStatusUpdateRet);
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//        threadMaps.put(svcRunningTaskBean.getTask_id(), thread);
//        return 0;
//    }
//
//    private void lifeMonitor() {
//        logger.info("启动生命周期监控线程.");
//        Thread lifeThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    Iterator<Thread> it = threadMaps.values().iterator();
//                    while (it.hasNext()) {
//                        Thread _thread = it.next();
//                        if (_thread.getState().equals(Thread.State.TERMINATED)) {
//                            logger.info("[remove] {}-{} status={}", _thread.getId(), _thread.getName(), _thread.getState());
//                            it.remove();
//                            it = threadMaps.values().iterator();
//                        }
//                    }
//                    SleepUtil.sleepMilliSecond(500L);
//                }
//            }
//        });
//        lifeThread.setDaemon(true);
//        lifeThread.start();
//    }
}

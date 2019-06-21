package com.cqx.testspringboot.activiti.api;

import com.cqx.testspringboot.activiti.model.*;
import com.cqx.testspringboot.activiti.server.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程管理API层
 *
 * @author chenqixu
 */
@RestController
@RequestMapping("${cmcc.web.servlet-path}/activiti")
public class ProcessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Resource
    private ProcessService processService;

    @RequestMapping(value = "/queryProcessDefinition", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo queryProcessDefinition() {
        RespInfo respInfo = new RespInfo();
        logger.info("查询流程定义");
        List<ProcessTemplate> list = processService.queryProcessDefinition();
        respInfo.setRespData(list);
        return respInfo;
    }

    @RequestMapping(value = "/startProcessObjs", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo startProcessObjs(@RequestBody ProcessInfo processInfo) {
        RespInfo respInfo = new RespInfo();
        logger.info("多个对象同时发起审批流程:{}", processInfo);
        List<ProcessInfo> processInfos = processService.startProcessObjs(processInfo);
        respInfo.setRespData(processInfos);
        return respInfo;
    }

    @RequestMapping(value = "/startProcess", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo startProcess(@RequestBody ProcessInfo processInfo) {
        RespInfo respInfo = new RespInfo();
        logger.info("发起流程:{}", processInfo);
        String processInstanceId = processService.startProcess(processInfo);
        respInfo.setRespData(processInstanceId);
        return respInfo;
    }

    @RequestMapping(value = "/delProcess", method = {RequestMethod.DELETE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo delProcess(@RequestBody ProcessInfo processInfo) {
        RespInfo respInfo = new RespInfo();
        logger.info("删除流程:{}", processInfo);
        processService.delProcess(processInfo);
        return respInfo;
    }

    @RequestMapping(value = "/startAndCompleteProcess", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo startAndCompleteProcess(@RequestBody ProcessInfo processInfo) {
        RespInfo respInfo = new RespInfo();
        logger.info("发起流程并结束第一个任务:{}", processInfo);
        String processInstanceId = processService.startAndCompleteProcess(processInfo);
        respInfo.setRespData(processInstanceId);
        return respInfo;
    }

    @RequestMapping(value = "/claimTask", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo claimTask(@RequestBody ProcessTask processTask) {
        RespInfo respInfo = new RespInfo();
        logger.info("领取任务:{}", processTask);
        processService.claimTask(processTask);
        return respInfo;
    }

    @RequestMapping(value = "/completeTask", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo completeTask(@RequestBody ProcessTask processTask) {
        RespInfo respInfo = new RespInfo();
        logger.info("结束流程任务:{}", processTask);
        ProcessInfo processInfo = processService.completeTask(processTask);
        respInfo.setRespData(processInfo);
        return respInfo;
    }

    @RequestMapping(value = "/claimAndCompleteTask", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo claimAndCompleteTask(@RequestBody ProcessTask processTask) {
        RespInfo respInfo = new RespInfo();
        logger.info("审批流程:{}", processTask);
        ProcessInfo processInfo = processService.claimAndCompleteTask(processTask);
        respInfo.setRespData(processInfo);
        return respInfo;
    }

    @RequestMapping(value = "/claimAndCompleteTasks", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo claimAndCompleteTasks(@RequestBody List<ProcessTask> processTasks) {
        RespInfo respInfo = new RespInfo();
        logger.info("批量审批流程任务:{}", processTasks);
        List<ProcessInfo> processInfos = processService.claimAndCompleteTasks(processTasks);
        respInfo.setRespData(processInfos);
        return respInfo;
    }

    @RequestMapping(value = "/myProcess", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo myProcess(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询我发起的流程:{}", processQuery);
        List<ProcessInfo> processInfos = processService.myProcess(processQuery);
        logger.info("返回数据:{}", processInfos);
        respInfo.setRespData(processInfos);
        return respInfo;
    }

    @RequestMapping(value = "/myProcessTask", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo myProcessTask(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询我的流程任务:{}", processQuery);
        List<ProcessTask> processTasks = processService.myProcessTask(processQuery);
        logger.info("返回数据:{}", processTasks);
        respInfo.setRespData(processTasks);
        return respInfo;
    }

    @RequestMapping(value = "/myProcessRecord", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object myProcessRecord(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询我的流程记录:{}", processQuery);
        List<ProcessInfo> processInfos = processService.myProcessRecord(processQuery);
        logger.info("返回数据:{}", processInfos);
        respInfo.setRespData(processInfos);
        return respInfo;
    }

    @RequestMapping(value = "/myProcessTaskRecord", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo myProcessTaskRecord(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询我审批过(已结束)的流程:{}", processQuery);
        List<ProcessInfo> processInfos = processService.myProcessTaskRecord(processQuery);
        logger.info("返回数据:{}", processInfos);
        respInfo.setRespData(processInfos);
        return respInfo;
    }

    @RequestMapping(value = "/myProcessTasking", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo myProcessTasking(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询我审批过(审批中)的流程:{}", processQuery);
        List<ProcessInfo> processInfos = processService.myProcessTasking(processQuery);
        logger.info("返回数据:{}", processInfos);
        respInfo.setRespData(processInfos);
        return respInfo;
    }

    @RequestMapping(value = "/instanceTasks/{instanceId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo instanceTasks(@PathVariable String instanceId) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询流程实例的任务审批列表:{}", instanceId);
        List<ProcessTask> processTasks = processService.instanceTasks(instanceId);
        respInfo.setRespData(processTasks);
        return respInfo;
    }

    @RequestMapping(value = "/myProcessing", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo myProcessing(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询我的待审批流程:{}", processQuery);
        respInfo = processService.myProcessing(processQuery);
        logger.info("返回数据:{}", respInfo);
        return respInfo;
    }

    @RequestMapping(value = "/myProcesses", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo myProcesses(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询我参与过的流程:{}", processQuery);
        respInfo = processService.myProcesses(processQuery);
        logger.info("返回数据:{}", respInfo);
        return respInfo;
    }

    @RequestMapping(value = "/queryObjRecentProcess", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RespInfo queryObjRecentProcess(@RequestBody ProcessQuery processQuery) {
        RespInfo respInfo = new RespInfo();
        logger.info("查询某个对象最新的审批信息:{}", processQuery);
        respInfo = processService.queryObjRecentProcess(processQuery);
        logger.info("返回数据:{}", respInfo);
        return respInfo;
    }

}

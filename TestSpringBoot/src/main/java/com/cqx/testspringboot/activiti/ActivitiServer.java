package com.cqx.testspringboot.activiti;

import com.cqx.testspringboot.activiti.model.ProcessTask;
import com.cqx.testspringboot.activiti.util.ActivitiUtil;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ActivitiServer
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("activiti_test")
public class ActivitiServer {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiServer.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RuntimeService runtimeService;
    @Resource
    private IdentityService identityService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private RepositoryService repositoryService;

//    @RequestMapping("build")
//    public void buildTable() {
//        //创建一个流程成引擎对像
//        ProcessEngineConfiguration conf = ProcessEngineConfiguration.
//            createStandaloneInMemProcessEngineConfiguration();
//        //设置数据源
//        conf.setJdbcDriver("com.mysql.jdbc.Driver");
//        conf.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti?useUnicode=true&amp;characterEncoding=UTF-8&useSSL=false&autoReconnect=true");
//        conf.setJdbcUsername("udap");
//        conf.setJdbcPassword("udap");
//        //设置自动创建表
//        conf.setDatabaseSchemaUpdate("true");
//        //在创建引擎对象的时候自动创建表
//        ProcessEngine processEngine = conf.buildProcessEngine();
//    }

    @RequestMapping("/startProcess")
    public void startProcess(@PathVariable String instanceId) {
        logger.info("startProcess");
        String user_id = "1001";
        String apply_id = "1";
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("apply_id", apply_id);
        // 启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("RESOURCE_APPLY_FOR_TENANT", user_id, varMap);
        logger.info("processInstance.id：{}", processInstance.getId());
    }

    /**
     * 根据流程实例ID查询所有任务
     *
     * @param instanceId
     * @return
     */
    @RequestMapping(value = "/instanceTasks/{instanceId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void instanceTasks(@PathVariable String instanceId) {
        logger.info("instanceTasks，instanceId：{}", instanceId);
        List<ProcessTask> processTaskList = new ArrayList<>();
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().
                processInstanceId(instanceId).orderByTaskCreateTime().asc().list();
        if (historicTaskInstanceList != null && historicTaskInstanceList.size() > 0) {
            for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
                ProcessTask processTask = getProcessTask(historicTaskInstance);
                if (processTask != null && StringUtils.isNotEmpty(processTask.getCurrentTaskId())) {
                    processTaskList.add(processTask);
                    logger.info("processTask：{}", processTask);
                }
            }
        }
    }

    private ProcessTask getProcessTask(HistoricTaskInstance historicTaskInstance) {
        ProcessTask processTask = new ProcessTask();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
        List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(historicProcessInstance.getId()).excludeTaskVariables().list();
        processTask.setVarMap(ActivitiUtil.getVarMap(varInstanceList));

        processTask.setProcessKey(historicProcessInstance.getProcessDefinitionKey());
        processTask.setProcessName(historicProcessInstance.getProcessDefinitionName());
        processTask.setProcessInstanceId(historicProcessInstance.getId());
        processTask.setStartUser(historicProcessInstance.getStartUserId());
        processTask.setApplyTime(sdf.format(historicProcessInstance.getStartTime()));

        List<HistoricVariableInstance> varTaskList = historyService.createHistoricVariableInstanceQuery().taskId(historicTaskInstance.getId()).list();
        processTask.setLocalVarMap(ActivitiUtil.getVarMap(varTaskList));

        processTask.setCurrentTaskId(historicTaskInstance.getId());
        processTask.setCurrentTaskName(historicTaskInstance.getName());
        processTask.setCurrentUserId(historicTaskInstance.getAssignee());
        processTask.setCreateTime(sdf.format(historicTaskInstance.getCreateTime()));
        if (historicTaskInstance.getEndTime() != null) {
            processTask.setEndTime(sdf.format(historicTaskInstance.getEndTime()));
        } else {
            processTask.setCurrentCandidateUsers(this.getCandidateUsers(historicTaskInstance.getId()));
        }
        return processTask;
    }

    private String getCandidateUsers(String taskId) {
        String candidateUsers = null;
        try {
            List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(taskId);
            candidateUsers = "";
            if (identityLinkList != null && identityLinkList.size() > 0) {
                for (IdentityLink identityLink : identityLinkList) {
                    if (StringUtils.isNotEmpty(identityLink.getUserId())) {
                        candidateUsers += "," + identityLink.getUserId();
                    }
                }
                if (StringUtils.isNotEmpty(candidateUsers)) {
                    candidateUsers = candidateUsers.substring(1);
                }
            }
        } catch (Exception e) {
            logger.error("获取任务用户异常:{}", e.getMessage());
        }
        return candidateUsers;
    }
}

package com.cqx.testspringboot.activiti.server;

import com.cqx.testspringboot.activiti.dao.NativeHistoricProcessInstanceFacadeDao;
import com.cqx.testspringboot.activiti.dao.NativeTaskFacadeDao;
import com.cqx.testspringboot.activiti.exception.ProcessException;
import com.cqx.testspringboot.activiti.exception.ProcessExceptionCode;
import com.cqx.testspringboot.activiti.model.*;
import com.cqx.testspringboot.activiti.util.ActivitiUtil;
import com.cqx.testspringboot.activiti.util.ValidateParamUtil;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程管理服务层
 *
 * @author chenqixu
 */
@Service
public class ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private IdentityService identityService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private NativeTaskFacadeDao nativeTaskFacadeDao;
    @Resource
    private NativeHistoricProcessInstanceFacadeDao nativeHistoricProcessInstanceFacadeDao;

    /**
     * 查询流程定义列表
     *
     * @return
     */
    public List<ProcessTemplate> queryProcessDefinition() {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        // 添加排序条件降序排序
        query.orderByProcessDefinitionVersion().latestVersion().desc();
        List<ProcessDefinition> list = query.list();
        List<ProcessTemplate> tempList = new ArrayList<>();
        ProcessTemplate pt = null;
        for (ProcessDefinition pd : list) {
            pt = new ProcessTemplate();
            BeanUtils.copyProperties(pd, pt);
            tempList.add(pt);
        }

        return tempList;
    }

    /**
     * 多个对象同时发起审批流程
     *
     * @param processInfo
     * @return
     */
    public List<ProcessInfo> startProcessObjs(ProcessInfo processInfo) {
        ValidateParamUtil.ValidateProcessInfo(processInfo);

        if (processInfo.getVarMap() == null || processInfo.getVarMap().get("obj_list") == null) {
            throw new ProcessException("流程审批内容为空", ProcessExceptionCode.PARAM_VARMAP_ERROR);
        }
        List<Map<String, String>> objList = (ArrayList) processInfo.getVarMap().get("obj_list");
        if (objList == null || objList.size() == 0) {
            throw new ProcessException("流程审批内容对象为空", ProcessExceptionCode.PARAM_VARMAP_OBJLIST_ERROR);
        }

        ProcessInfo rtnProcessInfo;
        List<Map<String, String>> list;
        List<ProcessInfo> rtnProcessInfos = new ArrayList<>();
        for (Map map : objList) {
            rtnProcessInfo = new ProcessInfo();
            list = new ArrayList<>();
            list.add(map);
            processInfo.getVarMap().put("obj_list", list);
            rtnProcessInfo.setObjId((String) map.get("objId"));
            identityService.setAuthenticatedUserId(processInfo.getStartUser());

            // 开始流程
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInfo.getProcessKey(), processInfo.getVarMap());
            rtnProcessInfo.setProcessInstanceId(processInstance.getId());
            rtnProcessInfos.add(rtnProcessInfo);
        }

        //设置流程实例发起人
        /*identityService.setAuthenticatedUserId(processInfo.getStartUser());

        // 开始流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInfo.getProcessKey(), processInfo.getVarMap());

        return processInstance.getId();*/

        return rtnProcessInfos;
    }

    /**
     * 发起流程
     *
     * @param processInfo
     * @return
     */
    public String startProcess(ProcessInfo processInfo) {
        ValidateParamUtil.ValidateProcessInfo(processInfo);

        //设置流程实例发起人
        identityService.setAuthenticatedUserId(processInfo.getStartUser());

        // 开始流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInfo.getProcessKey(), processInfo.getVarMap());

        return processInstance.getId();
    }

    /**
     * 删除流程实例
     *
     * @param processInfo
     * @return
     */
    public void delProcess(ProcessInfo processInfo) {
        if (processInfo == null) {
            throw new ProcessException("参数不能为空", ProcessExceptionCode.NON_PARAM);
        }
        if (StringUtils.isEmpty(processInfo.getProcessInstanceId())) {
            throw new ProcessException("流程实例ID不能为空", ProcessExceptionCode.NON_PROCESS_INSTANCE_ID);
        }

        // 删除流程
        runtimeService.deleteProcessInstance(processInfo.getProcessInstanceId(), "系统删除");
    }

    /**
     * 发起流程并走完第一个任务(如果第一个任务设置为Assignee,则varMap必须指派任务审核人)
     *
     * @param processInfo
     * @return
     */
    public String startAndCompleteProcess(ProcessInfo processInfo) {
        ValidateParamUtil.ValidateProcessInfo(processInfo);

        //设置流程实例发起人
        identityService.setAuthenticatedUserId(processInfo.getStartUser());
        // 开始流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInfo.getProcessKey(), processInfo.getVarMap());
        // 查询当前任务
        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        // 发起人直接领取任务
        taskService.claim(currentTask.getId(), processInfo.getStartUser());
        //发起人结束任务
        taskService.complete(currentTask.getId());

        return processInstance.getId();
    }

    /**
     * 我发起的流程(审批中)
     *
     * @param processQuery
     * @return
     */
    public List<ProcessInfo> myProcess(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery().startedBy(processQuery.getUserId());
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            processInstanceQuery = processInstanceQuery.processDefinitionKey(processQuery.getProcessKey());
        }
        List<ProcessInstance> instanceList = processInstanceQuery.list();
        List<ProcessInfo> processInfos = new ArrayList<>();
        for (ProcessInstance instance : instanceList) {
            ProcessInfo processInfo = getProcessInfo(instance, null);
            processInfos.add(processInfo);
        }
        return processInfos;
    }

    /**
     * 我的待审批流程
     *
     * @param processQuery
     * @return
     */
    public List<ProcessTask> myProcessTask(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);

        //待审批
        TaskQuery taskQuery = taskService.createTaskQuery().orderByTaskCreateTime().desc().taskCandidateOrAssigned(processQuery.getUserId());
        if (StringUtils.equals("0", processQuery.getIsCandidate())) {
            //包括未领取
            taskQuery = taskService.createTaskQuery().taskAssignee(processQuery.getUserId());
        }
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            taskQuery.processDefinitionKey(processQuery.getProcessKey());
        }
        List<Task> taskList = taskQuery.list();

        List<ProcessTask> processTaskList = new ArrayList<>();
        for (Task task : taskList) {
            ProcessTask processTask = getProcessTask(task);
            if (processTask != null) {
                processTaskList.add(processTask);
            }
        }
        return processTaskList;
    }

    /**
     * 领取任务
     *
     * @param processTask
     */
    public void claimTask(ProcessTask processTask) {
        ValidateParamUtil.ValidateProcessTask(processTask);
        //领取任务
        taskService.claim(processTask.getCurrentTaskId(), processTask.getCurrentUserId());
    }

    /**
     * 结束流程任务
     *
     * @param processTask
     * @return
     */
    public ProcessInfo completeTask(ProcessTask processTask) {
        ValidateParamUtil.ValidateProcessTask(processTask);

        Task task = taskService.createTaskQuery().taskId(processTask.getCurrentTaskId()).singleResult();
        if (task == null) {
            throw new ProcessException("任务不能为空", ProcessExceptionCode.NON_PROCESS_CURRENT_TASK);
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(task.getProcessInstanceId()).singleResult();
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcessInstanceId(processInstance.getProcessInstanceId());
        processInfo.setProcessKey(processInstance.getProcessDefinitionKey());

        processInfo.setStartUser(processInstance.getStartUserId());
        if (processTask.getLocalVarMap() != null) {
            taskService.setVariablesLocal(processTask.getCurrentTaskId(), processTask.getLocalVarMap());
        }
        //领取任务
        taskService.claim(processTask.getCurrentTaskId(), processTask.getCurrentUserId());
        taskService.complete(processTask.getCurrentTaskId(), processTask.getVarMap());

        //判断流程实例是否已经结束
        processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null || processInstance.isEnded()) {
            processInfo.setIsEnded("1");
        }

        return processInfo;
    }

    /**
     * 领取并结束任务
     *
     * @param processTask
     * @return
     */
    public ProcessInfo claimAndCompleteTask(ProcessTask processTask) {
        ValidateParamUtil.ValidateProcessTask(processTask);

        Task task = taskService.createTaskQuery().taskId(processTask.getCurrentTaskId()).singleResult();

        if (task == null) {
            throw new ProcessException("任务不能为空", ProcessExceptionCode.NON_PROCESS_CURRENT_TASK);
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(task.getProcessInstanceId()).singleResult();
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcessInstanceId(processInstance.getProcessInstanceId());
        processInfo.setProcessKey(processInstance.getProcessDefinitionKey());
        processInfo.setStartUser(processInstance.getStartUserId());
        processInfo.setVarMap(runtimeService.getVariables(processInstance.getId()));
        if (processTask.getLocalVarMap() != null) {
            taskService.setVariablesLocal(processTask.getCurrentTaskId(), processTask.getLocalVarMap());
        }
        //领取任务
        taskService.claim(processTask.getCurrentTaskId(), processTask.getCurrentUserId());
        taskService.complete(processTask.getCurrentTaskId(), processTask.getVarMap());

        processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null || processInstance.isEnded()) {
            processInfo.setIsEnded("1");
        }

        return processInfo;
    }

    /**
     * 批量领取并结束任务
     *
     * @param processTasks
     * @return
     */
    public List<ProcessInfo> claimAndCompleteTasks(List<ProcessTask> processTasks) {
        if (processTasks == null || processTasks.size() == 0) {
            throw new ProcessException("参数不能为空", ProcessExceptionCode.NON_PARAM);
        }
        for (ProcessTask processTask : processTasks) {
            ValidateParamUtil.ValidateProcessTask(processTask);
        }
        List<ProcessInfo> processInfos = new ArrayList<>();
        for (ProcessTask processTask : processTasks) {
            Task task = taskService.createTaskQuery().taskId(processTask.getCurrentTaskId()).singleResult();
            if (task != null) {
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                        processInstanceId(task.getProcessInstanceId()).singleResult();
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setProcessInstanceId(processInstance.getProcessInstanceId());
                processInfo.setProcessKey(processInstance.getProcessDefinitionKey());
                processInfo.setStartUser(processInstance.getStartUserId());
                processInfo.setVarMap(runtimeService.getVariables(processInstance.getId()));
                if (processTask.getLocalVarMap() != null) {
                    taskService.setVariablesLocal(processTask.getCurrentTaskId(), processTask.getLocalVarMap());
                }
                //领取任务
                taskService.claim(processTask.getCurrentTaskId(), processTask.getCurrentUserId());
                taskService.complete(processTask.getCurrentTaskId(), processTask.getVarMap());

                processInstance = runtimeService.createProcessInstanceQuery().
                        processInstanceId(task.getProcessInstanceId()).singleResult();
                if (processInstance == null || processInstance.isEnded()) {
                    processInfo.setIsEnded("1");
                }
                processInfos.add(processInfo);
            }
        }
        return processInfos;
    }

    /**
     * 我发起的流程(已结束)
     *
     * @param processQuery
     * @return
     */
    public List<ProcessInfo> myProcessRecord(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .startedBy(processQuery.getUserId()).finished().orderByProcessInstanceEndTime().desc();
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            historicProcessInstanceQuery.processDefinitionKey(processQuery.getProcessKey());
        }

        List<HistoricProcessInstance> hisProInstance = historicProcessInstanceQuery.list();

        List<ProcessInfo> processInfos = new ArrayList<>();
        for (HistoricProcessInstance hisInstance : hisProInstance) {
            processInfos.add(getProcessInfo(hisInstance));
        }
        return processInfos;
    }

    /**
     * 我的流程审批历史(已结束)
     *
     * @param processQuery
     * @return
     */
    public List<ProcessInfo> myProcessTaskRecord(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .involvedUser(processQuery.getUserId()).finished().orderByProcessInstanceEndTime().desc();
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            historicProcessInstanceQuery.processDefinitionKey(processQuery.getProcessKey());
        }
        List<HistoricProcessInstance> hisProInstance = historicProcessInstanceQuery.list();

        List<ProcessInfo> processInfos = new ArrayList<>();
        for (HistoricProcessInstance hisInstance : hisProInstance) {
            processInfos.add(getProcessInfo(hisInstance));
        }
        return processInfos;
    }

    /**
     * 我的流程审批历史(审批中)
     *
     * @param processQuery
     * @return
     */
    public List<ProcessInfo> myProcessTasking(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .involvedUser(processQuery.getUserId()).unfinished().orderByProcessInstanceEndTime().desc();
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            historicProcessInstanceQuery.processDefinitionKey(processQuery.getProcessKey());
        }
        List<HistoricProcessInstance> hisProInstance = historicProcessInstanceQuery.list();

        List<ProcessInfo> processInfos = new ArrayList<>();
        for (HistoricProcessInstance hisInstance : hisProInstance) {
            processInfos.add(getProcessInfo(hisInstance));
        }
        return processInfos;
    }

    /**
     * 根据流程实例ID查询所有任务
     *
     * @param instanceId
     * @return
     */
    public List<ProcessTask> instanceTasks(String instanceId) {
        List<ProcessTask> processTaskList = new ArrayList<>();
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().
                processInstanceId(instanceId).orderByTaskCreateTime().asc().list();
        if (historicTaskInstanceList != null && historicTaskInstanceList.size() > 0) {
            for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
                ProcessTask processTask = getProcessTask(historicTaskInstance);
                if (processTask != null && StringUtils.isNotEmpty(processTask.getCurrentTaskId())) {
                    processTaskList.add(processTask);
                }
            }
        }

        return processTaskList;
    }

    /**
     * 我的待审批流程
     *
     * @param processQuery
     * @return
     */
    public RespInfo myProcessing(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);

        RespInfo respInfo = new RespInfo();
        //待审批
        List<Task> taskList = nativeTaskFacadeDao.getBaseDao().queryUserTasks(processQuery);

        List<ProcessInfo> processInfoList = new ArrayList<>();
        for (Task task : taskList) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            processInfoList.add(getProcessInfo(processInstance, task));
        }
        respInfo.setDataTotalCount(nativeTaskFacadeDao.getBaseDao().queryUserTaskCount(processQuery));
        respInfo.setRespData(processInfoList);
        return respInfo;
    }

    /**
     * 查询我参与过的流程
     *
     * @param processQuery
     * @return
     */
    public RespInfo myProcesses(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);

        RespInfo respInfo = new RespInfo();

        List<HistoricProcessInstance> historicProcessInstanceList = nativeHistoricProcessInstanceFacadeDao.getBaseDao().queryHistoricInstances(processQuery);
        List<ProcessInfo> processInfos = new ArrayList<>();
        for (HistoricProcessInstance hisInstance : historicProcessInstanceList) {
            processInfos.add(getProcessInfo(hisInstance));
        }
        respInfo.setDataTotalCount(nativeHistoricProcessInstanceFacadeDao.getBaseDao().queryHistoricInstanceCount(processQuery));
        respInfo.setRespData(processInfos);
        return respInfo;
    }

    /**
     * 查询某个对象最新的审批信息
     *
     * @param processQuery
     * @return
     */
    public RespInfo queryObjRecentProcess(ProcessQuery processQuery) {
        ValidateParamUtil.ValidateProcessQuery(processQuery);
        if (StringUtils.isEmpty(processQuery.getObjId())) {
            throw new ProcessException("必须输入对象ID", ProcessExceptionCode.PARAM_VARMAP_OBJLIST_ERROR);
        }
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().desc();

        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            historicProcessInstanceQuery.processDefinitionKey(processQuery.getProcessKey());
        }
        if (StringUtils.isNotEmpty(processQuery.getModule())) {
            historicProcessInstanceQuery.variableValueEquals("module", processQuery.getModule());
        }
        if (StringUtils.isNotEmpty(processQuery.getObjType())) {
            historicProcessInstanceQuery.variableValueEquals("objType", processQuery.getObjType());
        }

        List<HistoricProcessInstance> historicProcessInstanceList = historicProcessInstanceQuery.list();

        String processInstanceId = "";
        boolean flag = false;
        //对查询的结果进行遍历，找到该对象对应的最新流程审批，并返回流程实例下的任务列表
        for (HistoricProcessInstance hisInstance : historicProcessInstanceList) {
            List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(hisInstance.getId()).list();
            Map<String, Object> varMap = ActivitiUtil.getVarMap(varInstanceList);
            if (varMap != null) {
                List<LinkedHashMap<String, Object>> objList = (List<LinkedHashMap<String, Object>>) varMap.get("obj_list");
                for (LinkedHashMap<String, Object> map : objList) {
                    if (StringUtils.equals(map.get("objId").toString(), processQuery.getObjId())) {
                        processInstanceId = hisInstance.getId();
                        flag = true;
                        break;
                    }
                }
                if (flag) break;
            }
        }

        RespInfo respInfo = new RespInfo();

        if (flag) {
            List<ProcessTask> processTasks = this.instanceTasks(processInstanceId);
            respInfo.setRespData(processTasks);
        } else {
            throw new ProcessException("未找到审批记录", ProcessExceptionCode.PROCESS_EXCEPTION);
        }
        return respInfo;
    }

    //根据流程实例、当前任务获得流程信息
    private ProcessInfo getProcessInfo(ProcessInstance instance, Task task) {
        Map<String, Object> varMap = runtimeService.getVariables(instance.getId());

        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcessInstanceId(instance.getId());
        processInfo.setProcessKey(instance.getProcessDefinitionKey());
        processInfo.setProcessName(instance.getProcessDefinitionName());
        processInfo.setStartUser(instance.getStartUserId());
        processInfo.setVarMap(varMap);
        processInfo.setApplyTime(sdf.format(instance.getStartTime()));
        processInfo.setIsEnded("0");
        if (task == null && !instance.isEnded()) {
            task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        }
        if (task != null) {
            processInfo.setCurrentTaskId(task.getId());
            processInfo.setCurrentTaskName(task.getName());
            processInfo.setCreateTime(sdf.format(task.getCreateTime()));
            processInfo.setCurrentAssignee(task.getAssignee());
            processInfo.setCurrentCandidateUsers(this.getCandidateUsers(task.getId()));
            processInfo.setCurrentCandidateGroups(this.getCandidateGroups(task.getId()));
        }
        return processInfo;
    }

    //根据历史流程实例获得流程信息
    private ProcessInfo getProcessInfo(HistoricProcessInstance hisInstance) {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcessKey(hisInstance.getProcessDefinitionKey());
        processInfo.setProcessName(hisInstance.getProcessDefinitionName());
        processInfo.setProcessInstanceId(hisInstance.getId());
        processInfo.setStartUser(hisInstance.getStartUserId());
        processInfo.setApplyTime(sdf.format(hisInstance.getStartTime()));
        List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(hisInstance.getId()).list();
        processInfo.setVarMap(ActivitiUtil.getVarMap(varInstanceList));
        processInfo.setIsEnded("1");

        List<Task> taskList = taskService.createTaskQuery().processInstanceId(hisInstance.getId()).list();
        //查看是否有用户审批结束的任务
        if (taskList != null && taskList.size() > 0) {
            //未结束
            processInfo.setIsEnded("0");
            //取得当前任务
            processInfo.setCurrentTaskId(taskList.get(0).getId());
            processInfo.setCurrentTaskName(taskList.get(0).getName());
            processInfo.setCurrentAssignee(taskList.get(0).getAssignee());
            processInfo.setCurrentCandidateUsers(this.getCandidateUsers(taskList.get(0).getId()));
            processInfo.setCurrentCandidateGroups(this.getCandidateGroups(taskList.get(0).getId()));
            processInfo.setCreateTime(sdf.format(taskList.get(0).getCreateTime()));
        }
        return processInfo;
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

    private String getCandidateGroups(String taskId) {
        String candidateGroups = null;
        try {
            List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(taskId);
            candidateGroups = "";
            if (identityLinkList != null && identityLinkList.size() > 0) {
                for (IdentityLink identityLink : identityLinkList) {
                    if (StringUtils.isNotEmpty(identityLink.getGroupId())) {
                        candidateGroups += "," + identityLink.getGroupId();
                    }
                }
                if (StringUtils.isNotEmpty(candidateGroups)) {
                    candidateGroups = candidateGroups.substring(1);
                }
            }
        } catch (Exception e) {
            logger.error("获取任务角色异常:{}", e.getMessage());
        }
        return candidateGroups;
    }


    private ProcessTask getProcessTask(Task task) {
        ProcessTask processTask = new ProcessTask();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        processTask.setVarMap(runtimeService.getVariables(task.getProcessInstanceId()));
        processTask.setLocalVarMap(task.getTaskLocalVariables());
        processTask.setProcessKey(processInstance.getProcessDefinitionKey());
        processTask.setProcessName(processInstance.getProcessDefinitionName());
        processTask.setProcessInstanceId(processInstance.getProcessInstanceId());
        processTask.setStartUser(processInstance.getStartUserId());
        processTask.setApplyTime(sdf.format(processInstance.getStartTime()));
        processTask.setCurrentTaskId(task.getId());
        processTask.setCurrentTaskName(task.getName());
        processTask.setCurrentUserId(task.getAssignee());
        //processTask.setCurrentCandidateUsers(this.getCandidateUsers(task.getId()));
        processTask.setCreateTime(sdf.format(task.getCreateTime()));
        return processTask;
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

}

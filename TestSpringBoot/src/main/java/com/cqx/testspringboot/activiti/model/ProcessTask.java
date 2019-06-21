package com.cqx.testspringboot.activiti.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * 用户审批任务
 *
 * @author chenqixu
 */
public class ProcessTask {

    private String processKey;

    private String processName;

    private String processInstanceId;

    private String startUser;

    private String applyTime; //流程创建时间

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentTaskId; //当前环节ID

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentTaskName; //当前环节

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentUserId; //当前环节审核人

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentCandidateUsers; //当前组任务人员

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentCandidateGroups; //当前组任务角色

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createTime; //任务创建时间

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String endTime;   //任务结束时间

    private Map<String, Object> varMap; //流程实例变量

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> localVarMap; //任务范围变量

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public String getCurrentTaskName() {
        return currentTaskName;
    }

    public void setCurrentTaskName(String currentTaskName) {
        this.currentTaskName = currentTaskName;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentCandidateUsers() {
        return currentCandidateUsers;
    }

    public void setCurrentCandidateUsers(String currentCandidateUsers) {
        this.currentCandidateUsers = currentCandidateUsers;
    }

    public String getCurrentCandidateGroups() {
        return currentCandidateGroups;
    }

    public void setCurrentCandidateGroups(String currentCandidateGroups) {
        this.currentCandidateGroups = currentCandidateGroups;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Map<String, Object> getVarMap() {
        return varMap;
    }

    public void setVarMap(Map<String, Object> varMap) {
        this.varMap = varMap;
    }

    public Map<String, Object> getLocalVarMap() {
        return localVarMap;
    }

    public void setLocalVarMap(Map<String, Object> localVarMap) {
        this.localVarMap = localVarMap;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

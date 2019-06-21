package com.cqx.testspringboot.activiti.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * @author chenqixu
 */
public class ProcessInfo {

    private String processInstanceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String processKey;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String processName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String startUser;

    private Map<String, Object> varMap;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String objId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String applyTime;   //流程申请时间

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createTime;  //当前环节创建时间

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentTaskId; //当前环节ID

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentTaskName; //当前环节

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentAssignee; //当前环节审核人

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentCandidateUsers; //当前组任务人员

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentCandidateGroups; //当前组任务角色

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isEnded;//流程是否已结束

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

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

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public Map<String, Object> getVarMap() {
        return varMap;
    }

    public void setVarMap(Map<String, Object> varMap) {
        this.varMap = varMap;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getCurrentAssignee() {
        return currentAssignee;
    }

    public void setCurrentAssignee(String currentAssignee) {
        this.currentAssignee = currentAssignee;
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

    public String getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(String isEnded) {
        this.isEnded = isEnded;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

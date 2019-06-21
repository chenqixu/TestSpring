package com.cqx.testspringboot.activiti.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 流程查询条件
 *
 * @author chenqixu
 */
public class ProcessQuery {

    private String userId;

    private String userRole;

    private String processKey;

    //是否查询待领取的任务
    private String isCandidate;

    private String isEnded; //0审批中,1已结束

    private String startTime;

    private String endTime;

    private String module;//所属模块

    private String objType;//对象类型

    private String objId;//对象ID

    private String objName;//对象名称模糊查找

    private int nowPage;  //分页第几页

    private int pageSize; //分页每页数量

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getIsCandidate() {
        return isCandidate;
    }

    public void setIsCandidate(String isCandidate) {
        this.isCandidate = isCandidate;
    }

    public String getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(String isEnded) {
        this.isEnded = isEnded;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

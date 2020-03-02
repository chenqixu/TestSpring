package com.cqx.testspring.webservice.common.model;

/**
 * ServiceBean
 *
 * @author chenqixu
 */
public class ServiceBean extends WSBean {
    private String serviceName;
    private String serviceImplClass;
    private String wsType;
    private String serviceType;
    private String serviceStatus;
    private String createTime;
    private String createUser = "10000000";
    private String modifyTime;
    private String modifyUser = "10000000";
    private String project_name;

    public ServiceBean() {
    }

    public String getWsType() {
        return this.wsType;
    }

    public void setWsType(String wsType) {
        this.wsType = wsType;
    }

    public String getProject_name() {
        return this.project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getServiceImplClass() {
        return this.serviceImplClass;
    }

    public void setServiceImplClass(String serviceImplClass) {
        this.serviceImplClass = serviceImplClass;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceStatus() {
        return this.serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}

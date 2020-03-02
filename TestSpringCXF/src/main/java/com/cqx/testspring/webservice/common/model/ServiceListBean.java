package com.cqx.testspring.webservice.common.model;

import java.util.List;
import java.util.Vector;

/**
 * ServiceListBean
 *
 * @author chenqixu
 */
public class ServiceListBean {
    private List<ServiceBean> serviceBeanList = new Vector();
    private String start = "0";
    private String pageSize = "20";
    private String totalCount = "0";

    public ServiceListBean() {
    }

    public String getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<ServiceBean> getServiceBeanList() {
        return this.serviceBeanList;
    }

    public void setServiceBeanList(List<ServiceBean> serviceBeanList) {
        this.serviceBeanList = serviceBeanList;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}

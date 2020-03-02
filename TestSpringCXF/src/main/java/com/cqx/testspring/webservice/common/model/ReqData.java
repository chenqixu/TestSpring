package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * ReqData
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReqData")
public class ReqData extends WSBean {
    private static final long serialVersionUID = 1L;
    private String reqObjectString;
    private String start = "0";
    private String pageCount = "20";
    private String totalCount = "0";

    public ReqData() {
    }

    public String getReqObjectString() {
        return this.reqObjectString;
    }

    public void setReqObjectString(String reqObjectString) {
        this.reqObjectString = reqObjectString;
    }

    public String getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
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

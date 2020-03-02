package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * System
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "System")
public class System extends WSBean {
    private static final long serialVersionUID = 1L;
    private String reqSource = "59190111";
    private String reqModule;
    private String reqType;
    private String reqTime;
    private String reqSeq;
    private String reqVersion = "1.0";

    public System() {
    }

    public String getReqVersion() {
        return this.reqVersion;
    }

    public void setReqVersion(String reqVersion) {
        this.reqVersion = reqVersion;
    }

    public String getReqSeq() {
        return this.reqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }

    public String getReqSource() {
        return this.reqSource;
    }

    public void setReqSource(String reqSource) {
        this.reqSource = reqSource;
    }

    public String getReqTime() {
        return this.reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getReqModule() {
        return this.reqModule;
    }

    public void setReqModule(String reqModule) {
        this.reqModule = reqModule;
    }

    public String getReqType() {
        return this.reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
}

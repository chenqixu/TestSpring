package com.cqx.testspring.webservice.common.model;

import com.cqx.testspring.webservice.common.util.other.Guid;
import com.cqx.testspring.webservice.common.util.other.SpringProperties;
import com.cqx.testspring.webservice.common.util.session.SessionFactory;
import com.cqx.testspring.webservice.common.util.xml.MessageHandler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * System20
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "System20")
public class System20 {
    private String ReqSource;
    private String ReqModule;
    private String ReqType;
    private String ReqTime;
    private String ReqSeq;
    private String ReqVersion = "1.0";
    private String SystemId;

    public System20() {
        try {
            this.SystemId = SpringProperties.getProperty("DefaultSystemId");
            this.ReqSeq = Guid.generate();
            this.ReqTime = MessageHandler.newInstance().getCurrentDate();
            this.ReqSource = SpringProperties.getProperty("DefaultReqSource");
        } catch (Exception var3) {
        }

        try {
            UserInfo userInfo = SessionFactory.getUserInfoSession();
            if (userInfo != null && userInfo.getReq_source() != null && userInfo.getReq_source().trim().length() > 0) {
                this.ReqSource = userInfo.getReq_source();
            }
        } catch (Exception var2) {
        }

    }

    public String getReqVersion() {
        return this.ReqVersion;
    }

    public void setReqVersion(String reqVersion) {
        this.ReqVersion = reqVersion;
    }

    public String getReqSeq() {
        return this.ReqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.ReqSeq = reqSeq;
    }

    public String getReqSource() {
        return this.ReqSource;
    }

    public void setReqSource(String reqSource) {
        this.ReqSource = reqSource;
    }

    public String getReqTime() {
        return this.ReqTime;
    }

    public void setReqTime(String reqTime) {
        this.ReqTime = reqTime;
    }

    public String getReqModule() {
        return this.ReqModule;
    }

    public void setReqModule(String reqModule) {
        this.ReqModule = reqModule;
    }

    public String getReqType() {
        return this.ReqType;
    }

    public void setReqType(String reqType) {
        this.ReqType = reqType;
    }

    public String getSystemId() {
        return this.SystemId;
    }

    public void setSystemId(String systemId) {
        this.SystemId = systemId;
    }
}

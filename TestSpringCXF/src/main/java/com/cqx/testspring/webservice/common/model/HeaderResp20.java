package com.cqx.testspring.webservice.common.model;

import com.cqx.testspring.webservice.common.util.xml.MessageHandler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * HeaderResp20
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "HeaderResp20"
)
public class HeaderResp20 {
    private String RespResult = "0";
    private String RespTime = MessageHandler.newInstance().getCurrentDate();
    private String RespCode = "Success";
    private String RespDesc = "成功";
    private String TokenCode;
    private String svcReqTime;
    private String svcRespTime;
    private String sqlReqTime;
    private String sqlRespTime;
    private String RespIp;

    public HeaderResp20() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            this.RespIp = address.getHostAddress();
        } catch (UnknownHostException var3) {
            var3.printStackTrace();
        }

    }

    public String getRespResult() {
        return this.RespResult;
    }

    public void setRespResult(String respResult) {
        this.RespResult = respResult;
    }

    public String getRespTime() {
        return this.RespTime;
    }

    public void setRespTime(String respTime) {
        this.RespTime = respTime;
    }

    public String getRespCode() {
        return this.RespCode;
    }

    public void setRespCode(String respCode) {
        this.RespCode = respCode;
    }

    public String getRespDesc() {
        return this.RespDesc;
    }

    public void setRespDesc(String respDesc) {
        this.RespDesc = respDesc;
    }

    public String getTokenCode() {
        return this.TokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.TokenCode = tokenCode;
    }

    public String getSvcReqTime() {
        return this.svcReqTime;
    }

    public void setSvcReqTime(String svcReqTime) {
        this.svcReqTime = svcReqTime;
    }

    public String getSvcRespTime() {
        return this.svcRespTime;
    }

    public void setSvcRespTime(String svcRespTime) {
        this.svcRespTime = svcRespTime;
    }

    public String getSqlReqTime() {
        return this.sqlReqTime;
    }

    public void setSqlReqTime(String sqlReqTime) {
        this.sqlReqTime = sqlReqTime;
    }

    public String getSqlRespTime() {
        return this.sqlRespTime;
    }

    public void setSqlRespTime(String sqlRespTime) {
        this.sqlRespTime = sqlRespTime;
    }

    public String getRespIp() {
        return this.RespIp;
    }

    public void setRespIp(String respIp) {
        this.RespIp = respIp;
    }
}

package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * User
 *
 * @author chenqixu
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User")
public class User extends WSBean {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String passWord;
    private String verifyCode;

    public User() {
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

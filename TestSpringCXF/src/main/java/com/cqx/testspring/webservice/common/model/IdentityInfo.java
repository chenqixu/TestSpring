package com.cqx.testspring.webservice.common.model;

import com.cqx.testspring.webservice.common.util.session.SessionFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * IdentityInfo
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentityInfo")
public class IdentityInfo extends WSBean {
    private static final long serialVersionUID = 1L;
    private String ClientID;
    private String PassWord;

    public IdentityInfo() {
        try {
            UserInfo userInfo = SessionFactory.getUserInfoSession();
            if (userInfo != null) {
                this.ClientID = userInfo.getUser_id();
            }
        } catch (Exception var2) {
        }

    }

    public String getClientID() {
        return this.ClientID;
    }

    public void setClientID(String clientID) {
        this.ClientID = clientID;
    }

    public String getPassWord() {
        return this.PassWord;
    }

    public void setPassWord(String passWord) {
        this.PassWord = passWord;
    }
}

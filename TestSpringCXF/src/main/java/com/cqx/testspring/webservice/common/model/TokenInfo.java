package com.cqx.testspring.webservice.common.model;

import com.cqx.testspring.webservice.common.util.session.SessionFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * TODO
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TokenInfo")
public class TokenInfo extends WSBean {
    private static final long serialVersionUID = 1L;
    private String TokenCode;

    public TokenInfo() {
        try {
            UserInfo userInfo = SessionFactory.getUserInfoSession();
            if (userInfo != null) {
                this.TokenCode = userInfo.getToken_code();
            }
        } catch (Exception var2) {
        }

    }

    public String getTokenCode() {
        return this.TokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.TokenCode = tokenCode;
    }
}

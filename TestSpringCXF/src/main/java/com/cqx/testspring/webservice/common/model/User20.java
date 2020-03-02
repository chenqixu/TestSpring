package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * User20
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User20")
public class User20 {
    private IdentityInfo IdentityInfo = new IdentityInfo();
    private TokenInfo TokenInfo = new TokenInfo();

    public User20() {
    }

    public String getVerifyCode() {
        return this.TokenInfo.getTokenCode();
    }

    public void setVerifyCode(String verifyCode) {
        this.TokenInfo.setTokenCode(verifyCode);
    }

    public String getClientId() {
        return this.IdentityInfo.getClientID();
    }

    public void setClientId(String clientId) {
        this.IdentityInfo.setClientID(clientId);
    }

    public String getPassWord() {
        return this.IdentityInfo.getPassWord();
    }

    public void setPassWord(String passWord) {
        this.IdentityInfo.setPassWord(passWord);
    }

    public IdentityInfo getIdentityInfo() {
        return this.IdentityInfo;
    }

    public void setIdentityInfo(IdentityInfo identityInfo) {
        this.IdentityInfo = identityInfo;
    }

    public TokenInfo getTokenInfo() {
        return this.TokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.TokenInfo = tokenInfo;
    }
}


package com.cqx.testspring.webservice.common.model;

import com.cqx.testspring.webservice.common.util.session.SessionFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * AppInfo20
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppInfo20")
public class AppInfo20 {
    private String OptType;
    private String PrivId;
    private String FromPriv;
    private String PageId;
    private String PageDesc;
    private String ActionId;
    private String ActionDesc;
    private String AopMethodId;
    private String login_seq;

    public String getAopMethodId() {
        return this.AopMethodId;
    }

    public void setAopMethodId(String aopMethodId) {
        this.AopMethodId = aopMethodId;
    }

    public AppInfo20() {
        try {
            UserInfo userInfo = SessionFactory.getUserInfoSession();
            if (userInfo != null) {
                if (userInfo.getPriv_id() != null && userInfo.getPriv_id().trim().length() > 0) {
                    this.PrivId = userInfo.getPriv_id();
                }

                if (userInfo.getFrom_priv() != null && userInfo.getFrom_priv().trim().length() > 0) {
                    this.FromPriv = userInfo.getFrom_priv();
                }

                if (userInfo.getLogin_seq() != null && userInfo.getLogin_seq().trim().length() > 0) {
                    this.login_seq = userInfo.getLogin_seq();
                }
            }
        } catch (Exception var2) {
        }

    }

    public String getOptType() {
        return this.OptType;
    }

    public void setOptType(String optType) {
        this.OptType = optType;
    }

    public String getPrivId() {
        return this.PrivId;
    }

    public void setPrivId(String privId) {
        this.PrivId = privId;
    }

    public String getFromPriv() {
        return this.FromPriv;
    }

    public void setFromPriv(String fromPriv) {
        this.FromPriv = fromPriv;
    }

    public String getPageId() {
        return this.PageId;
    }

    public void setPageId(String pageId) {
        this.PageId = pageId;
    }

    public String getPageDesc() {
        return this.PageDesc;
    }

    public void setPageDesc(String pageDesc) {
        this.PageDesc = pageDesc;
    }

    public String getActionId() {
        return this.ActionId;
    }

    public void setActionId(String actionId) {
        this.ActionId = actionId;
    }

    public String getActionDesc() {
        return this.ActionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.ActionDesc = actionDesc;
    }

    public String getLogin_seq() {
        return this.login_seq;
    }

    public void setLogin_seq(String login_seq) {
        this.login_seq = login_seq;
    }
}

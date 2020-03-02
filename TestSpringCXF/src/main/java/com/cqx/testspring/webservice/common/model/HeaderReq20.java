package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * HeaderReq20
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderReq20")
public class HeaderReq20 {
    private User20 User = new User20();
    private System20 System = new System20();
    private Route20 Route = new Route20();
    private AppInfo20 AppInfo = new AppInfo20();
    private String ReqIp;

    public HeaderReq20() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            this.ReqIp = address.getHostAddress();
        } catch (UnknownHostException var3) {
            var3.printStackTrace();
        }

    }

    public AppInfo20 getAppInfo() {
        return this.AppInfo;
    }

    public void setAppInfo(AppInfo20 appInfo) {
        this.AppInfo = appInfo;
    }

    public User20 getUser() {
        return this.User;
    }

    public void setUser(User20 user) {
        this.User = user;
    }

    public System20 getSystem() {
        return this.System;
    }

    public void setSystem(System20 system) {
        this.System = system;
    }

    public Route20 getRoute() {
        return this.Route;
    }

    public void setRoute(Route20 route) {
        this.Route = route;
    }

    public String getReqIp() {
        return this.ReqIp;
    }

    public void setReqIp(String reqIp) {
        this.ReqIp = reqIp;
    }
}

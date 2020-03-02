package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * HeaderReq
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderReq")
public class HeaderReq extends WSBean {
    private static final long serialVersionUID = 1L;
    private User user = new User();
    private System system = new System();
    private Route route = new Route();

    public HeaderReq() {
    }

    public Route getRoute() {
        return this.route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public System getSystem() {
        return this.system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

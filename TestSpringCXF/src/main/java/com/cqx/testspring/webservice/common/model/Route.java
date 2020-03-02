package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Route
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Route")
public class Route extends WSBean {
    private static final long serialVersionUID = 1L;
    private String routeType;
    private String routeValue;

    public Route() {
    }

    public String getRouteType() {
        return this.routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getRouteValue() {
        return this.routeValue;
    }

    public void setRouteValue(String routeValue) {
        this.routeValue = routeValue;
    }
}

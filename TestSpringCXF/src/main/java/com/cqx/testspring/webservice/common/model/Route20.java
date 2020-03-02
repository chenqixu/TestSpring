package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Route20
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Route20")
public class Route20 {
    private String RouteType;
    private String RouteId;

    public Route20() {
    }

    public String getRouteId() {
        return this.RouteId;
    }

    public void setRouteId(String routeId) {
        this.RouteId = routeId;
    }

    public String getRouteType() {
        return this.RouteType;
    }

    public void setRouteType(String routeType) {
        this.RouteType = routeType;
    }
}

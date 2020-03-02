package com.cqx.testspring.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * BodyRespIntf
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BodyRespIntf")
public interface BodyRespIntf {
    RespDataIntf getRespData();

    void setRespData(RespDataIntf var1);

    String getTotalCount();

    void setTotalCount(String var1);
}

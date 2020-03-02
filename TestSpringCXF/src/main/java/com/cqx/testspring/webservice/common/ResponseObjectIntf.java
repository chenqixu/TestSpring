package com.cqx.testspring.webservice.common;

import com.cqx.testspring.webservice.common.model.HeaderResp20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * ResponseObjectIntf
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseObjectIntf")
public interface ResponseObjectIntf {
    BodyRespIntf getBodyResp();

    void setBodyResp(BodyRespIntf var1);

    HeaderResp20 getHeaderResp();

    void setHeaderResp(HeaderResp20 var1);
}

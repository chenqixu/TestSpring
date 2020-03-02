package com.cqx.testspring.webservice.common;

import com.cqx.testspring.webservice.common.model.HeaderReq20;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * RequestObjectIntf
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestObjectIntf")
public interface RequestObjectIntf {
    BodyReqIntf getBodyReq();

    void setBodyReq(BodyReqIntf var1);

    HeaderReq20 getHeaderReq();

    void setHeaderReq(HeaderReq20 var1);
}

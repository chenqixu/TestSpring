package com.cqx.testspring.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * TODO
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BodyReqIntf")
public interface BodyReqIntf {
    ReqDataIntf getReqData();

    void setReqData(ReqDataIntf var1);

    String getPageCount();

    void setPageCount(String var1);

    String getStart();

    void setStart(String var1);
}


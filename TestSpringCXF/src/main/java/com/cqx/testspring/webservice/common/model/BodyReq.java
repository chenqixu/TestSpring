package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * BodyReq
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BodyReq")
public class BodyReq extends WSBean {
    private static final long serialVersionUID = 1L;
    private ReqData reqData;

    public BodyReq() {
    }

    public ReqData getReqData() {
        return this.reqData;
    }

    public void setReqData(ReqData reqData) {
        this.reqData = reqData;
    }
}

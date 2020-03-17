package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * CommonRespObject
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonRespObject")
public class CommonRespObject {
    private String resp_conent;
    private String resq_class;

    public String getResp_conent() {
        return resp_conent;
    }

    public void setResp_conent(String resp_conent) {
        this.resp_conent = resp_conent;
    }

    public String getResq_class() {
        return resq_class;
    }

    public void setResq_class(String resq_class) {
        this.resq_class = resq_class;
    }
}

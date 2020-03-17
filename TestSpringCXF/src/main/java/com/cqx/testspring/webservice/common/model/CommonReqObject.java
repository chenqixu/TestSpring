package com.cqx.testspring.webservice.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * CommonReqObject
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonReqObject")
public class CommonReqObject {
    private String mgt_name;
    private String func_name;
    private String req_content;
    private String req_class;

    public String getReq_content() {
        return req_content;
    }

    public void setReq_content(String req_content) {
        this.req_content = req_content;
    }

    public String getReq_class() {
        return req_class;
    }

    public void setReq_class(String req_class) {
        this.req_class = req_class;
    }

    public String getMgt_name() {
        return mgt_name;
    }

    public void setMgt_name(String mgt_name) {
        this.mgt_name = mgt_name;
    }

    public String getFunc_name() {
        return func_name;
    }

    public void setFunc_name(String func_name) {
        this.func_name = func_name;
    }
}

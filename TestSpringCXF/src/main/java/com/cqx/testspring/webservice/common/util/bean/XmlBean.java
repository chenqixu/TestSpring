package com.cqx.testspring.webservice.common.util.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * XmlBean
 *
 * @author chenqixu
 */
public class XmlBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String service_id = null;
    private String result_type = "0";
    private String req_Source = "601010";
    private String module_id = "10";
    private String start = "1";
    private String pageCount = "15";
    private String rowCount = "0";
    private String totalnum = "0";
    private List rowList = new Vector();
    private String pageValue = "";
    private String is_page_submit = "false";
    private String ISSEARCH = "NOSEARCH";
    private String export_type = "0";
    private String verify_code = "";
    private CityOperLoginBean operInfoBean = new CityOperLoginBean();

    public XmlBean() {
    }

    public CityOperLoginBean getOperInfoBean() {
        return this.operInfoBean;
    }

    public void setOperInfoBean(CityOperLoginBean operInfoBean) {
        this.operInfoBean = operInfoBean;
    }

    public String getModule_id() {
        return this.module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getReq_Source() {
        return this.req_Source;
    }

    public void setReq_Source(String req_Source) {
        this.req_Source = req_Source;
    }

    public String getResult_type() {
        return this.result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public String getService_id() {
        return this.service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getVerify_code() {
        return this.verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getExport_type() {
        return this.export_type;
    }

    public void setExport_type(String export_type) {
        this.export_type = export_type;
    }

    public String getIs_page_submit() {
        return this.is_page_submit;
    }

    public void setIs_page_submit(String is_page_submit) {
        this.is_page_submit = is_page_submit;
    }

    public String getISSEARCH() {
        return this.ISSEARCH;
    }

    public void setISSEARCH(String issearch) {
        this.ISSEARCH = issearch;
    }

    public String getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageValue() {
        return this.pageValue;
    }

    public void setPageValue(String pageValue) {
        this.pageValue = pageValue;
    }

    public String getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public List getRowList() {
        return this.rowList;
    }

    public void setRowList(List rowList) {
        this.rowList = rowList;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTotalnum() {
        return this.totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }
}

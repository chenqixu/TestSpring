package com.cqx.testspring.webservice.operhis.model.javabean;

/**
 * OperhisResultBean
 *
 * @author chenqixu
 */
public class OperhisResultBean {
    private String opt_id;
    private String opt_result;

    public String getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }

    public String getOpt_result() {
        return opt_result;
    }

    public void setOpt_result(String opt_result) {
        this.opt_result = opt_result;
    }

    public String toString() {
        return "[opt_id]" + opt_id + ",[opt_result]" + opt_result;
    }
}

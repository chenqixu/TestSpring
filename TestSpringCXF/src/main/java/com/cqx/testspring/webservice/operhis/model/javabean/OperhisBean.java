package com.cqx.testspring.webservice.operhis.model.javabean;

/**
 * OperhisBean
 *
 * @author chenqixu
 */
public class OperhisBean {
    private String user_id;
    private String oper_desc;
    private String oper_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOper_desc() {
        return oper_desc;
    }

    public void setOper_desc(String oper_desc) {
        this.oper_desc = oper_desc;
    }

    public String getOper_time() {
        return oper_time;
    }

    public void setOper_time(String oper_time) {
        this.oper_time = oper_time;
    }

    public String toString() {
        return "[user_id]" + user_id + ",[oper_desc]" + oper_desc + ",[oper_time]" + oper_time;
    }
}

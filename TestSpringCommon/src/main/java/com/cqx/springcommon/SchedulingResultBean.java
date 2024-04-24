package com.cqx.springcommon;

/**
 * 返回结果
 *
 * @author chenqixu
 */
public class SchedulingResultBean {
    private int res_code;
    private String task_id;
    private String body;

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
}

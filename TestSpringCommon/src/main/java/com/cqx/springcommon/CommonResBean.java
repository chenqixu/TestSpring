package com.cqx.springcommon;

/**
 * 返回bean
 *
 * @author chenqixu
 */
public class CommonResBean {
    private int ret_code = 0;// 0失败；1成功
    private String body = "";

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

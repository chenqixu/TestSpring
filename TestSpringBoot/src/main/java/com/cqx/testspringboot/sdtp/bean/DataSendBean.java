package com.cqx.testspringboot.sdtp.bean;

/**
 * DataSendBean
 *
 * @author chenqixu
 */
public class DataSendBean {
    private String rule;
    private String data;
    private String sdtpserver;
    private boolean lengthcheck;
    private String sdtpheader;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSdtpserver() {
        return sdtpserver;
    }

    public void setSdtpserver(String sdtpserver) {
        this.sdtpserver = sdtpserver;
    }

    public boolean isLengthcheck() {
        return lengthcheck;
    }

    public void setLengthcheck(boolean lengthcheck) {
        this.lengthcheck = lengthcheck;
    }

    public String getSdtpheader() {
        return sdtpheader;
    }

    public void setSdtpheader(String sdtpheader) {
        this.sdtpheader = sdtpheader;
    }
}

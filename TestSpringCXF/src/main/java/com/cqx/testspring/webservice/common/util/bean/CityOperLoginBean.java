package com.cqx.testspring.webservice.common.util.bean;

import java.io.Serializable;

/**
 * CityOperLoginBean
 *
 * @author chenqixu
 */
public class CityOperLoginBean implements Serializable {
    private String user_id;
    private String user_name;
    private String nick_name;
    private String region_id;
    private String region_name;
    private String org_id;
    private String org_name;
    private String city_id;
    private String city_name;
    private String region_type;
    private String mobile_phone;
    private String mail_addr;
    private String data_priv;
    private String headship;
    private String headshipname;
    private String verify_code;

    public CityOperLoginBean() {
    }

    public String getVerify_code() {
        return this.verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getCity_id() {
        return this.city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return this.city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getData_priv() {
        return this.data_priv;
    }

    public void setData_priv(String data_priv) {
        this.data_priv = data_priv;
    }

    public String getHeadship() {
        return this.headship;
    }

    public void setHeadship(String headship) {
        this.headship = headship;
    }

    public String getHeadshipname() {
        return this.headshipname;
    }

    public void setHeadshipname(String headshipname) {
        this.headshipname = headshipname;
    }

    public String getMail_addr() {
        return this.mail_addr;
    }

    public void setMail_addr(String mail_addr) {
        this.mail_addr = mail_addr;
    }

    public String getMobile_phone() {
        return this.mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getNick_name() {
        return this.nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getOrg_id() {
        return this.org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return this.org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getRegion_id() {
        return this.region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return this.region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_type() {
        return this.region_type;
    }

    public void setRegion_type(String region_type) {
        this.region_type = region_type;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}

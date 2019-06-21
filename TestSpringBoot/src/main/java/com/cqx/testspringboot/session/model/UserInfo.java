package com.cqx.testspringboot.session.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * UserInfo
 *
 * @author chenqixu
 */
public class UserInfo {
    private String token_code;
    private String user_id;
    private String user_name;
    private String nick_name;
    private String org_id;
    private String org_name;
    private String home_city;
    private String home_city_desc;
    private String home_county;
    private String home_county_desc;
    private String mobile_phone;
    private String mail_addr;
    private String password;
    private String sec_level;
    private String req_source;
    private String priv_id;
    private String from_priv;
    private String login_seq;

    public UserInfo() {
    }

    public String getReq_source() {
        return this.req_source;
    }

    public void setReq_source(String req_source) {
        this.req_source = req_source;
    }

    public String getPriv_id() {
        return this.priv_id;
    }

    public void setPriv_id(String priv_id) {
        this.priv_id = priv_id;
    }

    public String getFrom_priv() {
        return this.from_priv;
    }

    public void setFrom_priv(String from_priv) {
        this.from_priv = from_priv;
    }

    public String getHome_city() {
        return this.home_city;
    }

    public void setHome_city(String home_city) {
        this.home_city = home_city;
    }

    public String getHome_city_desc() {
        return this.home_city_desc;
    }

    public void setHome_city_desc(String home_city_desc) {
        this.home_city_desc = home_city_desc;
    }

    public String getHome_county() {
        return this.home_county;
    }

    public void setHome_county(String home_county) {
        this.home_county = home_county;
    }

    public String getHome_county_desc() {
        return this.home_county_desc;
    }

    public void setHome_county_desc(String home_county_desc) {
        this.home_county_desc = home_county_desc;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken_code() {
        return this.token_code;
    }

    public void setToken_code(String token_code) {
        this.token_code = token_code;
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

    public String getSec_level() {
        return this.sec_level;
    }

    public void setSec_level(String sec_level) {
        this.sec_level = sec_level;
    }

    public String getLogin_seq() {
        return this.login_seq;
    }

    public void setLogin_seq(String login_seq) {
        this.login_seq = login_seq;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

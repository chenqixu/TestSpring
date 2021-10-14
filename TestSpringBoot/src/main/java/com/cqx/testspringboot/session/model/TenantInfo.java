package com.cqx.testspringboot.session.model;

/**
 * TenantInfo
 *
 * @author chenqixu
 */
public class TenantInfo {
    private String tenant_id;
    private String tenant_name;
    private String tenant_ename;
    private String tenant_level;
    private String p_tenant_id;
    private String tenant_type;

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_ename() {
        return tenant_ename;
    }

    public void setTenant_ename(String tenant_ename) {
        this.tenant_ename = tenant_ename;
    }

    public String getTenant_level() {
        return tenant_level;
    }

    public void setTenant_level(String tenant_level) {
        this.tenant_level = tenant_level;
    }

    public String getP_tenant_id() {
        return p_tenant_id;
    }

    public void setP_tenant_id(String p_tenant_id) {
        this.p_tenant_id = p_tenant_id;
    }

    public String getTenant_type() {
        return tenant_type;
    }

    public void setTenant_type(String tenant_type) {
        this.tenant_type = tenant_type;
    }
}

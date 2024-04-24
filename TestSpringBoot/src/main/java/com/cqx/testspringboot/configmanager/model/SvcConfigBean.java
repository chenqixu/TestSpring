package com.cqx.testspringboot.configmanager.model;

/**
 * label_config
 *
 * @author chenqixu
 */
public class SvcConfigBean {
    private String config_name;
    private String param_name;
    private String param_value;

    public String getConfig_name() {
        return config_name;
    }

    public void setConfig_name(String config_name) {
        this.config_name = config_name;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public String getParam_value() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }
}

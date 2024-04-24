package com.cqx.springcommon;

/**
 * 容器节点注册bean
 *
 * @author chenqixu
 */
public class NodeReqBean {
    private String node_ip;
    private String node_name;
    private int node_port;
    private int node_source;
    private String node_type;//merge send customergrp analyse
    private boolean node_status = true;
    private long heartTime = System.currentTimeMillis();

    public void heart() {
        heartTime = System.currentTimeMillis();
    }

    /**
     * 节点是否离线，超时判断，60秒
     *
     * @return
     */
    public boolean isOff() {
        return System.currentTimeMillis() - heartTime >= 60 * 1000L;
    }

    public String getNode_ip() {
        return node_ip;
    }

    public void setNode_ip(String node_ip) {
        this.node_ip = node_ip;
    }

    public int getNode_source() {
        return node_source;
    }

    public void setNode_source(int node_source) {
        this.node_source = node_source;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public int getNode_port() {
        return node_port;
    }

    public void setNode_port(int node_port) {
        this.node_port = node_port;
    }

    public String getNode_type() {
        return node_type;
    }

    public void setNode_type(String node_type) {
        this.node_type = node_type;
    }

    public boolean isNode_status() {
        return node_status;
    }

    public void setNode_status(boolean node_status) {
        this.node_status = node_status;
    }
}

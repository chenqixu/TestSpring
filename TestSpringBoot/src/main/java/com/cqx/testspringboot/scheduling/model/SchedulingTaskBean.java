package com.cqx.testspringboot.scheduling.model;

import java.util.UUID;

/**
 * 任务信息
 *
 * @author chenqixu
 */
public class SchedulingTaskBean {
    private String uuid;

    public SchedulingTaskBean() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

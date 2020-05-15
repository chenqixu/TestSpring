package com.cqx.syncos.task.bean;

/**
 * TaskStatus
 *
 * @author chenqixu
 */
public class TaskStatus {
    private String task_name;
    private boolean scan_isRun;
    private boolean load_isRun;

    public boolean isStop() {
        return !scan_isRun && !load_isRun;
    }

    public boolean isScan_isRun() {
        return scan_isRun;
    }

    public void setScan_isRun(boolean scan_isRun) {
        this.scan_isRun = scan_isRun;
    }

    public boolean isLoad_isRun() {
        return load_isRun;
    }

    public void setLoad_isRun(boolean load_isRun) {
        this.load_isRun = load_isRun;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }
}

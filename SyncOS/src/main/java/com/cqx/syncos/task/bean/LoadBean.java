package com.cqx.syncos.task.bean;

/**
 * LoadBean
 *
 * @author chenqixu
 */
public class LoadBean {
    private String load_file_name;
    private long load_pos;

    public String getLoad_file_name() {
        return load_file_name;
    }

    public void setLoad_file_name(String load_file_name) {
        this.load_file_name = load_file_name;
    }

    public long getLoad_pos() {
        return load_pos;
    }

    public void setLoad_pos(long load_pos) {
        this.load_pos = load_pos;
    }
}

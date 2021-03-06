package com.cqx.syncos.task.bean;

/**
 * LoadBean
 *
 * @author chenqixu
 */
public class LoadBean {
    private String load_file_name;
    private int header_pos_next;

    public LoadBean(String load_file_name, int header_pos_next) {
        this.load_file_name = load_file_name;
        this.header_pos_next = header_pos_next;
    }

    public String getLoad_file_name() {
        return load_file_name;
    }

    public void setLoad_file_name(String load_file_name) {
        this.load_file_name = load_file_name;
    }

    public int getHeader_pos_next() {
        return header_pos_next;
    }

    public void setHeader_pos_next(int header_pos_next) {
        this.header_pos_next = header_pos_next;
    }
}

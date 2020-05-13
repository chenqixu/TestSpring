package com.cqx.syncos.task.bean;

/**
 * ScanCache
 *
 * @author chenqixu
 */
public class ScanCache {
    private long at_time;
    private int header_pos_next;

    public ScanCache(long at_time, int header_pos_next) {
        this.at_time = at_time;
        this.header_pos_next = header_pos_next;
    }

    public long getAt_time() {
        return at_time;
    }

    public void setAt_time(long at_time) {
        this.at_time = at_time;
    }

    public int getHeader_pos_next() {
        return header_pos_next;
    }

    public void setHeader_pos_next(int header_pos_next) {
        this.header_pos_next = header_pos_next;
    }
}
